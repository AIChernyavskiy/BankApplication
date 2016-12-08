import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sbertech.DAO.ClientDaoImpl;
import ru.sbertech.Logic.Client;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientDaoImplTest {

    public ApplicationContext testApplicationContext = new ClassPathXmlApplicationContext("application-context-test.xml");

    @Autowired
    ClientDaoImpl clientDao;

    @Before
    public void setUp(){
        Client client1 = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        client1.setName("VALERA");
        List<Client> clients1 = new ArrayList<>();
        clients1.add(client1);
        SessionFactory mockedSessionFactory =  Mockito.mock(SessionFactory.class);
        Session mockedSession = Mockito.mock(Session.class);
        Transaction mockedTransaction = Mockito.mock(Transaction.class);
        Query mockedQuery = Mockito.mock(Query.class);
        Mockito.when(mockedSession.createQuery(any(String.class))).thenReturn(mockedQuery);
        Mockito.when(mockedQuery.list()).thenReturn(clients1);
        Mockito.when(mockedQuery.uniqueResult()).thenReturn(client1);
        Mockito.when(mockedSessionFactory.openSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.load(Client.class, 1)).thenReturn(client1);
        Mockito.when(mockedSession.beginTransaction()).thenReturn(mockedTransaction);
        Mockito.when(mockedSessionFactory.getCurrentSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getTransaction()).thenReturn(mockedTransaction);
        clientDao.setSessionFactory(mockedSessionFactory);
    }
    @Test
    public void testSaveClient() {
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        client.setName("VALERA");
        clientDao.saveClient(client);
        List<Client> clients =  clientDao.clientList();
        Assert.assertEquals(client.getName(),clients.get(0).getName());
    }

    @Test
    public void testUpdateClient() {
        clientDao.updateClient("VALERA","VALERON");
    }


    @Test
    public void testClientlist() {
        Assert.assertNotNull(clientDao.clientList());
    }

    @Test
    public void testGetClientById() {
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        client.setName("VALERA");
        Assert.assertEquals(client.getName(),(clientDao.getClientById(1)).getName());
    }

    @Test
    public void testGetClientByName() {
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        client.setName("VALERA");
        Assert.assertEquals(client.getName(),(clientDao.getClientByName("VALERA")).getName());
    }

    @Test
    public void testRemoveClientById() {
        clientDao.removeClientById(1);
    }

    @Test
    public void testRemoveClientByName() {
        clientDao.removeClientByName("VALERA");
    }


}
