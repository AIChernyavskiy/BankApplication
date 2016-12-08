package ru.sbertech.DAO;


import ru.sbertech.Logic.Client;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public class ClientDaoImpl implements ClientDao {

    @Autowired
    SessionFactory sessionFactory;

    public ClientDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public ClientDaoImpl() {

    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void saveClient(Client client) throws HibernateException {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        session.saveOrUpdate(client);
        session.getTransaction().commit();
        System.out.println("Сохранили клиента в базу " + client);
    }

    @Override
    public void updateClient(String oldName, String newName) throws HibernateException {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        if (this.getClientByName(oldName) != null) {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query q = session.createQuery("update ru.sbertech.Logic.Client set name = :newName" +
                    " where name = :oldName");
            q.setParameter("newName", newName);
            q.setParameter("oldName", oldName);
            q.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public Client getClientById(long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Query q = session.createQuery("from ru.sbertech.Logic.Client where id = :id");
        q.setLong("id", id);
        Client client = (Client) q.uniqueResult();
        session.getTransaction().commit();
        return client;
    }

    @Override
    public Client getClientByName(String name) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Query q = session.createQuery("from ru.sbertech.Logic.Client where name = :name");
        q.setString("name", name);
        Client client = (Client) q.uniqueResult();
        session.getTransaction().commit();
        return client;
    }

    @Override
    public List<Client> clientList() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Query query = session.createQuery("from ru.sbertech.Logic.Client");
        List<Client> clients = query.list();
        session.getTransaction().commit();
        return clients;
    }

    @Override
    public void removeClientById(long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Client client = (Client) session.load(Client.class, id);
        if (client != null) {
            session.delete(client);
        }
        session.getTransaction().commit();
    }

    @Override
    public void removeClientByName(String name) throws HibernateException {
        Session session;
        Client client = this.getClientByName(name);
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        if (client != null) {
            session.delete(client);
        }
        session.getTransaction().commit();
    }
}
