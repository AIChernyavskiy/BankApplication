package ru.sbertech.Controller;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.sbertech.DAO.AccountDAOImpl;
import ru.sbertech.DAO.ClientDaoImpl;
import ru.sbertech.DAO.DocumentDaoImpl;
import ru.sbertech.Logic.Account;
import ru.sbertech.Logic.Client;
import ru.sbertech.Logic.Document;
import ru.sbertech.jms.SpringJmsConsumer;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static ru.sbertech.Main.applicationContext;

@Controller
public class DocumentController {

    public DocumentController() {
    }

    ClientDaoImpl clientDao = (ClientDaoImpl) applicationContext.getBean("ClientDaoImpl");
    AccountDAOImpl accountDAO = (AccountDAOImpl) applicationContext.getBean("AccountDAOImpl");
    DocumentDaoImpl documentDao = (DocumentDaoImpl) applicationContext.getBean("DocumentDaoImpl");
    SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
    public static final String PAGE_DOCUMENT = "Document";

    @RequestMapping(value = {"/Document"}, method = RequestMethod.GET)
    public ModelAndView frontPageDocument() {
        return new ModelAndView(PAGE_DOCUMENT);
    }

    @RequestMapping(value = {"/Document/CreateDocument"}, method = RequestMethod.GET)
    public ModelAndView createDocument(HttpServletRequest request) {
        return new ModelAndView(PAGE_DOCUMENT, "messageCreateDocument", checkAccountAndCreateDocument(request));
    }

    @RequestMapping(value = {"/Document/CreateDocumentAccountClient"}, method = RequestMethod.GET)
    public ModelAndView createDocumentAccountClient(HttpServletRequest request) {
        return new ModelAndView(PAGE_DOCUMENT, "messageCreateDocumentAccountClient", checkAccountClientAndCreateClientAccountDocument(request));
    }

    @RequestMapping(value = {"/Document/Delete"}, method = RequestMethod.GET)
    public ModelAndView deleteDocument(HttpServletRequest request) {
        return new ModelAndView(PAGE_DOCUMENT, "messageDelete", checkExistenceDocumentAndDeleteDocument(request));
    }

    @RequestMapping(value = {"/Document/Find"}, method = RequestMethod.GET)
    public ModelAndView createAccount(HttpServletRequest request) {
        return new ModelAndView(PAGE_DOCUMENT, "messageFind", checkAndFindDocument(request));
    }

    @RequestMapping(value = {"/PrintDocuments"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Document> findAllDocuments(HttpServletRequest request) {
        correctDocument();
        return documentDao.documentList();
    }


    public void correctDocument() {
        SpringJmsConsumer consumer = (SpringJmsConsumer) applicationContext.getBean("springJmsConsumer");
        Document document = (Document) applicationContext.getBean("ru.sbertech.Logic.Document");
        try {
            String message = null;
            while ((message = consumer.receiveMessage()) != null) {
                long id = Long.valueOf(message);
                document = documentDao.getDocumentById(id);
                if (document == null) {
                    continue;
                } else {
                    if (document.getAccountDT().checkSaldo(document.getSumma())) {
                        Session session;
                        try {
                            session = sessionFactory.getCurrentSession();
                        } catch (HibernateException e) {
                            session = sessionFactory.openSession();
                        }
                        try {
                            session.beginTransaction();
                            document.getAccountDT().saldoAfterTransactionCT(document.getSumma());
                            document.getAccountCT().saldoAfterTransactionDT(document.getSumma());
                            accountDAO.updateSaldoInTransaction(document.getAccountDT().getAccNum(), document.getAccountDT().getSaldo(), session);
                            accountDAO.updateSaldoInTransaction(document.getAccountCT().getAccNum(), document.getAccountCT().getSaldo(), session);
                            documentDao.updateStorno(id, true, session);
                            session.getTransaction().commit();
                        } catch (HibernateException e) {
                            session.getTransaction().rollback();
                        }
                    } else {
                        continue;
                    }
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    String checkAccountAndCreateDocument(HttpServletRequest request) {
        Session session;
        BigDecimal summa = BigDecimal.valueOf(Long.parseLong(request.getParameter("summa")));
        String purpose = request.getParameter("purpose");
        Account accountDT = accountDAO.getAccountByName(request.getParameter("accNumDT"));
        Account accountCT = accountDAO.getAccountByName(request.getParameter("accNumCT"));
        Document document = (Document) applicationContext.getBean("ru.sbertech.Logic.Document");
        if (accountDT == null) {
            return "The database is not a accountDT with number " + request.getParameter("accNumDT");
        } else if (accountCT == null) {
            return "The database is not a accountCT with number " + request.getParameter("accNumCT");
        } else if (accountCT.checkSaldo(summa)) {
            try {
                session = sessionFactory.getCurrentSession();
            } catch (HibernateException e) {
                session = sessionFactory.openSession();
            }
            try {
                session.beginTransaction();
                accountCT.saldoAfterTransactionCT(summa);
                accountDT.saldoAfterTransactionDT(summa);
                document.setAccountDT(accountDT);
                document.setAccountCT(accountCT);
                document.setSumma(summa);
                document.setPurpose(purpose);
                document.setDocDate(new Date());
                document.setStorno(false);
                accountDAO.updateSaldoInTransaction(accountCT.getAccNum(), accountCT.getSaldo(), session);
                accountDAO.updateSaldoInTransaction(accountDT.getAccNum(), accountDT.getSaldo(), session);
                documentDao.saveDocumentInTransaction(document, session);
                session.getTransaction().commit();
                return "Document " + document + " created";
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                return "Error " + e;
            }
        } else {
            return "The accountCT available " + accountCT.getSaldo();
        }
    }

    String checkExistenceDocumentAndDeleteDocument(HttpServletRequest request) {
        Document document = documentDao.getDocumentById(Long.parseLong(request.getParameter("idDocument")));
        if (document != null) {
            try {
                documentDao.removeDocumentById(document.getId());
            } catch (HibernateException e) {
                return "Error " + e;
            }
            return "Document " + document.getId() + " has been removed";
        } else {
            return "Document ID = " + request.getParameter("idDocument") + " does not exist";
        }
    }

    String checkAndFindDocument(HttpServletRequest request) {
        Document document = documentDao.getDocumentById(Long.parseLong(request.getParameter("idDocument")));
        if (document != null) {
            return document.toString();
        } else {
            return "The database is not a document with number " + request.getParameter("idDocument");
        }
    }

    String checkAccountClientAndCreateClientAccountDocument(HttpServletRequest request) {
        BigDecimal summa = BigDecimal.valueOf(Long.parseLong(request.getParameter("summa")));
        String purpose = request.getParameter("purpose");
        Account accountDT = accountDAO.getAccountByName(request.getParameter("accNumDT"));
        Account accountCT = accountDAO.getAccountByName(request.getParameter("accNumCT"));
        Client clientDT = clientDao.getClientByName(request.getParameter("clientNameDT"));
        Client clientCT = clientDao.getClientByName(request.getParameter("clientNameCT"));
        Document document = (Document) applicationContext.getBean("ru.sbertech.Logic.Document");
        if (request.getParameter("accNumDT").equals(request.getParameter("accNumCT"))) {
            return "Accounts number must not be the same";
        } else if (request.getParameter("clientNameDT").equals(request.getParameter("clientNameCT"))) {
            return "Clients names must not be the same";
        } else if (clientDT != null) {
            return "The client " + clientDT.getName() + " already exists, change the name and try again";
        } else if (clientCT != null) {
            return "The client " + clientCT.getName() + " already exists, change the name and try again";
        } else if (accountDT != null) {
            return "Account " + accountDT.getAccNum() + " already exsist";
        } else if (accountCT != null) {
            return "Account " + accountCT.getAccNum() + " already exsist";
        } else {
            accountDT = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
            accountCT = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
            clientDT = (Client) applicationContext.getBean("ru.sbertech.Logic.Client");
            clientCT = (Client) applicationContext.getBean("ru.sbertech.Logic.Client");
            clientDT.setName(request.getParameter("clientNameDT"));
            clientCT.setName(request.getParameter("clientNameCT"));
            accountDT.setAccNum(request.getParameter("accNumDT"));
            accountDT.setSaldo(BigDecimal.valueOf(Long.parseLong(request.getParameter("saldoDT"))));
            accountDT.setClient(clientDT);
            accountCT.setAccNum(request.getParameter("accNumCT"));
            accountCT.setSaldo(BigDecimal.valueOf(Long.parseLong(request.getParameter("saldoCT"))));
            accountCT.setClient(clientCT);
            if (accountCT.checkSaldo(summa)) {
                accountCT.saldoAfterTransactionCT(summa);
                accountDT.saldoAfterTransactionDT(summa);
                document.setAccountDT(accountDT);
                document.setAccountCT(accountCT);
                document.setSumma(summa);
                document.setPurpose(purpose);
                document.setDocDate(new Date());
                document.setStorno(false);
                try {
                    documentDao.saveDocument(document);
                    return "Document " + document + " created";
                } catch (HibernateException e) {
                    return "Error " + e;
                }
            } else {
                return "The accountCT available " + accountCT.getSaldo() + " the document is not saved";
            }
        }
    }

    public ClientDaoImpl getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientDaoImpl clientDao) {
        this.clientDao = clientDao;
    }

    public AccountDAOImpl getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAOImpl accountDAO) {
        this.accountDAO = accountDAO;
    }

    public DocumentDaoImpl getDocumentDao() {
        return documentDao;
    }

    public void setDocumentDao(DocumentDaoImpl documentDao) {
        this.documentDao = documentDao;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
