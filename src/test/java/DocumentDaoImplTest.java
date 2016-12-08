import org.hibernate.*;
import org.junit.Assert;
import org.junit.Before;
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
import ru.sbertech.DAO.DocumentDaoImpl;
import ru.sbertech.Logic.Account;
import ru.sbertech.Logic.Client;
import ru.sbertech.Logic.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DocumentDaoImplTest {

    public ApplicationContext testApplicationContext = new ClassPathXmlApplicationContext("application-context-test.xml");

    @Autowired
    DocumentDaoImpl documentDao;

    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void setUp(){
        Client clientCT = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Client clientDT = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Account accountCT = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        Account accountDT = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        Document document = (Document) testApplicationContext.getBean("ru.sbertech.Logic.Document");
        clientCT.setName("VALERA");
        accountCT.setAccNum("999");
        accountCT.setSaldo(new BigDecimal(9000));
        accountCT.setId(9);
        accountCT.setClient(clientCT);
        clientDT.setName("LEHA");
        accountDT.setAccNum("789");
        accountDT.setSaldo(new BigDecimal(7890));
        accountDT.setId(11);
        accountDT.setClient(clientDT);
        document.setId(1);
        document.setPurpose("");
        document.setSumma(new BigDecimal(1000));
        document.setDocDate(new Date());
        document.setStorno(false);
        document.setAccountCT(accountCT);
        document.setAccountDT(accountDT);
        List<Document> documents = new ArrayList<>();
        documents.add(document);
        SessionFactory mockedSessionFactory =  Mockito.mock(SessionFactory.class);
        Session mockedSession = Mockito.mock(Session.class);
        Transaction mockedTransaction = Mockito.mock(Transaction.class);
        Query mockedQuery = Mockito.mock(Query.class);
        Mockito.when(mockedSession.createQuery("update Document set storno = :storno where id = :id")).thenReturn(mockedQuery);
        Mockito.when(mockedSession.createQuery("from ru.sbertech.Logic.Document where id = :id")).thenReturn(mockedQuery);
        Mockito.when(mockedSession.createQuery("from ru.sbertech.Logic.Document")).thenReturn(mockedQuery);
        Mockito.when(mockedQuery.list()).thenReturn(documents);
        Mockito.when(mockedQuery.uniqueResult()).thenReturn(document);
        Mockito.when(mockedSessionFactory.openSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.load(Document.class, 1)).thenReturn(document);
        Mockito.when(mockedSession.load(Document.class, "")).thenReturn(document);
        Mockito.when(mockedSession.beginTransaction()).thenReturn(mockedTransaction);
        Mockito.when(mockedSessionFactory.getCurrentSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getTransaction()).thenReturn(mockedTransaction);
        documentDao.setSessionFactory(mockedSessionFactory);
    }

    @Test
    public void testSaveDocument() {
        Client clientCT = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Client clientDT = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Account accountCT = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        Account accountDT = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        Document document = (Document) testApplicationContext.getBean("ru.sbertech.Logic.Document");
        clientCT.setName("VALERA");
        accountCT.setAccNum("999");
        accountCT.setSaldo(new BigDecimal(9000));
        accountCT.setId(9);
        accountCT.setClient(clientCT);
        clientDT.setName("LEHA");
        accountDT.setAccNum("789");
        accountDT.setSaldo(new BigDecimal(7890));
        accountDT.setId(11);
        accountDT.setClient(clientDT);
        document.setId(1);
        document.setPurpose("");
        document.setSumma(new BigDecimal(1000));
        document.setDocDate(new Date());
        document.setStorno(false);
        document.setAccountCT(accountCT);
        document.setAccountDT(accountDT);
        documentDao.saveDocument(document);
    }

    @Test
    @Transactional("txManager")
    @Rollback
    public void testSaveDocumentInTransaction() {
        Client clientCT = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Client clientDT = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Account accountCT = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        Account accountDT = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        Document document = (Document) testApplicationContext.getBean("ru.sbertech.Logic.Document");
        clientCT.setName("VALERA");
        accountCT.setAccNum("999");
        accountCT.setSaldo(new BigDecimal(9000));
        accountCT.setClient(clientCT);
        clientDT.setName("LEHA");
        accountDT.setAccNum("789");
        accountDT.setSaldo(new BigDecimal(7890));
        accountDT.setClient(clientDT);
        document.setPurpose("");
        document.setSumma(new BigDecimal(1000));
        document.setDocDate(new Date());
        document.setStorno(false);
        document.setAccountCT(accountCT);
        document.setAccountDT(accountDT);
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        documentDao.saveDocumentInTransaction(document,session);
        session.getTransaction().rollback();
    }

    @Test
    @Transactional("txManager")
    @Rollback
    public void testUpdateStorno() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        documentDao.updateStorno(1,true,session);
        session.getTransaction().rollback();
    }

    @Test
    public void testDocumentList() {
        Assert.assertNotNull(documentDao.documentList());
    }

    @Test
    public void testGetDocumentById() {
        Client clientCT = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Client clientDT = (Client) testApplicationContext.getBean("ru.sbertech.Logic.Client");
        Account accountCT = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        Account accountDT = (Account) testApplicationContext.getBean("ru.sbertech.Logic.Account");
        Document document = (Document) testApplicationContext.getBean("ru.sbertech.Logic.Document");
        clientCT.setName("VALERA");
        accountCT.setAccNum("999");
        accountCT.setSaldo(new BigDecimal(9000));
        accountCT.setId(9);
        accountCT.setClient(clientCT);
        clientDT.setName("LEHA");
        accountDT.setAccNum("789");
        accountDT.setSaldo(new BigDecimal(7890));
        accountDT.setId(11);
        accountDT.setClient(clientDT);
        document.setId(1);
        document.setPurpose("");
        document.setSumma(new BigDecimal(1000));
        document.setDocDate(new Date());
        document.setStorno(false);
        document.setAccountCT(accountCT);
        document.setAccountDT(accountDT);
        Assert.assertEquals(document.getId(),documentDao.getDocumentById(1).getId());
    }

    @Test
    public void testRemoveDocumentById() {
        documentDao.removeDocumentById(1);
    }


}
