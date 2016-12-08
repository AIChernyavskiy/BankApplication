package ru.sbertech.Controller;


import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;

import static ru.sbertech.Main.applicationContext;

@Controller
public class IndexController {


    SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");

    public static final String PAGE_INDEX = "index";

    BrokerService broker = null;


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        if (broker == null) {
            try {
                broker = BrokerFactory.createBroker(new URI(
                        "broker:(tcp://localhost:61616)"));
                broker.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView(PAGE_INDEX);
    }

    @RequestMapping(value = {"/CreateDB"}, method = RequestMethod.GET)
    public ModelAndView indexDropAndCreateDB() {
        final String DROP_DOCUMENTS_TABLE = "DROP TABLE IF EXISTS DOCUMENTS;";
        final String DROP_ACCOUNTS_TABLE = "DROP TABLE IF EXISTS ACCOUNTS;";
        final String DROP_CLIENTS_TABLE = "DROP TABLE IF EXISTS CLIENTS;";
        final String CREATE_CLIENTS_TABLE = "create table CLIENTS (\n" +
                "id_client int(10) AUTO_INCREMENT,\n" +
                "name varchar(1000) NOT NULL,\n" +
                "PRIMARY KEY (id_client)\n" +
                ")";
        final String CREATE_ACCOUNTS_TABLE = "create table ACCOUNTS (\n" +
                "id_account int(10) AUTO_INCREMENT,\n" +
                "accNum varchar(1000) NOT NULL,\n" +
                "saldo number(10,2) NOT NULL,\n" +
                "client_id int(10) NOT NULL,\n" +
                "PRIMARY KEY (id_account),\n" +
                "FOREIGN KEY (client_id) REFERENCES CLIENTS (id_client)\n" +
                ")";
        final String CREATE_DOCUMENTS_TABLE = "create table DOCUMENTS (\n" +
                "id_document int(10) AUTO_INCREMENT,\n" +
                "accDT int(10) NOT NULL,\n" +
                "accCT int(10) NOT NULL,\n" +
                "summa number(10,2) NOT NULL,\n" +
                "purpose varchar(1000) NOT NULL,\n" +
                "docDate TIMESTAMP NOT NULL,\n" +
                "storno BOOLEAN NOT NULL,\n" +
                "PRIMARY KEY (id_document),\n" +
                "FOREIGN KEY (accDT) REFERENCES ACCOUNTS (id_account),\n" +
                "FOREIGN KEY (accCT) REFERENCES ACCOUNTS (id_account)\n" +
                ")";
        final String INSERT_VASYA = "INSERT INTO CLIENTS (NAME) VALUES ('VASYA')";
        final String INSERT_PETYA = "INSERT INTO CLIENTS (NAME) VALUES ('PETYA')";
        final String INSERT_KOLYA = "INSERT INTO CLIENTS (NAME) VALUES ('KOLYA')";
        final String INSERT_VANYA = "INSERT INTO CLIENTS (NAME) VALUES ('VANYA')";
        final String INSERT_ACCNUM_111 = "INSERT INTO ACCOUNTS (ACCNUM,SALDO,CLIENT_ID) VALUES ('111',1000,1)";
        final String INSERT_ACCNUM_222 = "INSERT INTO ACCOUNTS (ACCNUM,SALDO,CLIENT_ID) VALUES ('222',2000,2)";
        final String INSERT_ACCNUM_333 = "INSERT INTO ACCOUNTS (ACCNUM,SALDO,CLIENT_ID) VALUES ('333',3000,3)";
        final String INSERT_ACCNUM_444 = "INSERT INTO ACCOUNTS (ACCNUM,SALDO,CLIENT_ID) VALUES ('444',4000,4)";
        final String INSERT_ACCNUM_555 = "INSERT INTO ACCOUNTS (ACCNUM,SALDO,CLIENT_ID) VALUES ('555',5000,1)";
        final String INSERT_ACCNUM_666 = "INSERT INTO ACCOUNTS (ACCNUM,SALDO,CLIENT_ID) VALUES ('666',6000,2)";
        final String INSERT_ACCNUM_777 = "INSERT INTO ACCOUNTS (ACCNUM,SALDO,CLIENT_ID) VALUES ('777',7000,3)";
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        try {
            session.createSQLQuery(DROP_DOCUMENTS_TABLE).executeUpdate();
            session.createSQLQuery(DROP_ACCOUNTS_TABLE).executeUpdate();
            session.createSQLQuery(DROP_CLIENTS_TABLE).executeUpdate();
            session.createSQLQuery(CREATE_CLIENTS_TABLE).executeUpdate();
            session.createSQLQuery(CREATE_ACCOUNTS_TABLE).executeUpdate();
            session.createSQLQuery(CREATE_DOCUMENTS_TABLE).executeUpdate();
            session.createSQLQuery(INSERT_VASYA).executeUpdate();
            session.createSQLQuery(INSERT_PETYA).executeUpdate();
            session.createSQLQuery(INSERT_KOLYA).executeUpdate();
            session.createSQLQuery(INSERT_VANYA).executeUpdate();
            session.createSQLQuery(INSERT_ACCNUM_111).executeUpdate();
            session.createSQLQuery(INSERT_ACCNUM_222).executeUpdate();
            session.createSQLQuery(INSERT_ACCNUM_333).executeUpdate();
            session.createSQLQuery(INSERT_ACCNUM_444).executeUpdate();
            session.createSQLQuery(INSERT_ACCNUM_555).executeUpdate();
            session.createSQLQuery(INSERT_ACCNUM_666).executeUpdate();
            session.createSQLQuery(INSERT_ACCNUM_777).executeUpdate();
            session.getTransaction().commit();
            return new ModelAndView(PAGE_INDEX, "messageDB", "Data Base created");
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return new ModelAndView(PAGE_INDEX, "messageDB", "Data Base not created " + e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public BrokerService getBroker() {
        return broker;
    }

    public void setBroker(BrokerService broker) {
        this.broker = broker;
    }
}
