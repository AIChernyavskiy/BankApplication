import org.hibernate.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.sbertech.DAO.AccountDAOImpl;
import ru.sbertech.DAO.ClientDaoImpl;
import ru.sbertech.Logic.Account;
import ru.sbertech.Logic.Client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountDaoImplTest {

    public ApplicationContext testApplicationContext = new ClassPathXmlApplicationContext("application-context-test.xml");

    @Autowired
    ClientDaoImpl clientDao;

    @Autowired
    AccountDAOImpl accountDAO;

    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void setUp(){
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Account account = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        client.setName("VALERA");
        account.setAccNum("999");
        account.setSaldo(new BigDecimal(9000));
        account.setId(9);
        account.setClient(client);
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        SessionFactory mockedSessionFactory =  Mockito.mock(SessionFactory.class);
        Session mockedSession = Mockito.mock(Session.class);
        Transaction mockedTransaction = Mockito.mock(Transaction.class);
        Query mockedQuery = Mockito.mock(Query.class);
        Mockito.when(mockedSession.createQuery(any(String.class))).thenReturn(mockedQuery);
        Mockito.when(mockedQuery.list()).thenReturn(accounts);
        Mockito.when(mockedQuery.uniqueResult()).thenReturn(account);
        Mockito.when(mockedSessionFactory.openSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.load(Account.class,1)).thenReturn(account);
        Mockito.when(mockedSession.beginTransaction()).thenReturn(mockedTransaction);
        Mockito.when(mockedSessionFactory.getCurrentSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getTransaction()).thenReturn(mockedTransaction);
        clientDao.setSessionFactory(mockedSessionFactory);
        accountDAO.setSessionFactory(mockedSessionFactory);
    }

    @Test
    public void testSaveAccount() {
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Account account = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        client.setName("VALERA");
        account.setAccNum("999");
        account.setSaldo(new BigDecimal(9000));
        account.setId(9);
        account.setClient(client);
        accountDAO.saveAccount(account);
    }

    @Test
    public void testUpdateAccNum() {
        accountDAO.updateAccNum("999","101");
    }

    @Test
    public void testUpdateSaldo() {
        accountDAO.updateSaldo("999",new BigDecimal(10000));
    }

    @Test
    public void testUpdateClient() {
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        client.setName("PETYA");
        accountDAO.updateClient("101",client);
    }

    @Test
    @Transactional("txManager")
    @Rollback
    public void testUpdateSaldoInTransaction() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        accountDAO.setSessionFactory(sessionFactory);
        accountDAO.updateSaldoInTransaction("888",new BigDecimal(10000),session);
        session.getTransaction().rollback();
    }

    @Test
    public void testAccountList() {
        Assert.assertNotNull(accountDAO.accountList());
    }

    @Test
    public void testRemoveAccountById() {
        accountDAO.removeAccountById(1);
    }

    @Test
    public void testRemoveAccountByAccNum() {
        accountDAO.removeAccountByAccNum("999");
    }

    @Test
    public void testGetAccountById() {
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Account account = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        client.setName("VALERA");
        account.setAccNum("999");
        account.setSaldo(new BigDecimal(9000));
        account.setId(9);
        account.setClient(client);
        Assert.assertEquals(account.getId(),accountDAO.getAccountById(9).getId());
    }

    @Test
    public void testGetAccountByName() {
        Client client = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Account account = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        client.setName("VALERA");
        account.setAccNum("999");
        account.setSaldo(new BigDecimal(9000));
        account.setId(9);
        account.setClient(client);
        Assert.assertEquals(account.getAccNum(),accountDAO.getAccountByName("999").getAccNum());
    }
}

