package ru.sbertech.DAO;


import ru.sbertech.Logic.Document;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentDaoImpl implements DocumentDao {

    @Autowired
    SessionFactory sessionFactory;

    public DocumentDaoImpl() {
    }

    public DocumentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveDocument(Document document) throws HibernateException {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        session.save(document);
        session.getTransaction().commit();
        System.out.println("Сохранили документ в базу " + document);
    }

    @Override
    public void saveDocumentInTransaction(Document document, Session session) throws HibernateException {
        session.save(document);
    }

    @Override
    public void updateStorno(long id, boolean storno, Session session) {
        Query q = session.createQuery("update Document set storno = :storno" +
                " where id = :id");
        q.setParameter("id", id);
        q.setParameter("storno", storno);
        q.executeUpdate();
    }

    @Override
    public Document getDocumentById(long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Query q = session.createQuery("from ru.sbertech.Logic.Document where id = :id");
        q.setLong("id", id);
        Document document = (Document) q.uniqueResult();
        session.getTransaction().commit();
        return document;
    }

    @Override
    public List<Document> documentList() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Query query = session.createQuery("from ru.sbertech.Logic.Document");
        List<Document> documents = query.list();
        session.getTransaction().commit();
        return documents;
    }

    @Override
    public void removeDocumentById(long id) throws HibernateException {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Document document = (Document) session.load(Document.class, id);
        if (document != null) {
            session.delete(document);
        }
        session.getTransaction().commit();
    }

    @Override
    public void removeDocumentByPurpose(String purpose) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Document document = (Document) session.load(Document.class, purpose);
        if (document != null) {
            session.delete(document);
        }
        session.getTransaction().commit();
    }
}
