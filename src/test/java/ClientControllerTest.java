import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import ru.sbertech.Controller.ClientController;
import ru.sbertech.DAO.AccountDAOImpl;
import ru.sbertech.DAO.ClientDaoImpl;
import ru.sbertech.Logic.Client;


@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientControllerTest {

    public ApplicationContext testApplicationContext = new ClassPathXmlApplicationContext("application-context-test.xml");

    @Autowired
    ClientController clientController;

    @Autowired
    Client client;

    @Autowired
    ClientDaoImpl clientDao;


    @Before
    public void setUp(){
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        client.setName("GENYA");
        SessionFactory mockedSessionFactory =  Mockito.mock(SessionFactory.class);
        Session mockedSession = Mockito.mock(Session.class);
        Transaction mockedTransaction = Mockito.mock(Transaction.class);
        Mockito.when(mockedSessionFactory.openSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.beginTransaction()).thenReturn(mockedTransaction);
        Mockito.when(mockedSessionFactory.getCurrentSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getTransaction()).thenReturn(mockedTransaction);
    }

    @Test
    public void testFrontPageClient() {
        ModelAndView modelAndView = clientController.frontPageClient();
        Assert.assertEquals("Client",modelAndView.getViewName());
    }

    @Test
    public void testCreateClient() {
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        client.setName("GENYA");
        ClientController mockClientController = Mockito.mock(ClientController.class);
        Mockito.when(mockClientController.createClient(client)).thenReturn(new ModelAndView("Client","messageCreate","OK"));
        ModelAndView modelAndView = mockClientController.createClient(client);
        Assert.assertEquals("Client",modelAndView.getViewName());
        Assert.assertNotNull(modelAndView.getModel());
    }

    @Test
    public void testCheckExistenceClientAndSave() {
        client.setName("GENYA");
        Client client1 = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        client1.setName("GENYA");
        ClientDaoImpl mockClientDao = Mockito.mock(ClientDaoImpl.class);
        clientController.setClientDao(mockClientDao);
        Mockito.when(mockClientDao.getClientByName(client.getName())).thenReturn(client);
        Assert.assertEquals("The client " + client1.getName() + " already exists, change the name and try again",clientController.checkExistenceClientAndSave(client));
        clientController.setClientDao(clientDao);
    }

    @Test
    public void testCheckExistenceClientSave() {
        client.setName("GENYA");
        Client client1 = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        client1.setName("GENYA");
        ClientDaoImpl mockClientDao = Mockito.mock(ClientDaoImpl.class);
        clientController.setClientDao(mockClientDao);
        Mockito.when(mockClientDao.getClientByName(client.getName())).thenReturn(null);
        Mockito.doNothing().when(mockClientDao).saveClient(client);
        Assert.assertEquals("The client " + client1.getName() + " has been created",clientController.checkExistenceClientAndSave(client));
        clientController.setClientDao(clientDao);
    }

    @Test
    public void testPrintClients() {
        Assert.assertNotNull(clientController.printClients());
    }
}
