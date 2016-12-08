package ru.sbertech.DAO;


import ru.sbertech.Logic.Account;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sbertech.Logic.Client;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class AccountDAOImpl implements AccountDAO {

    @Autowired
    SessionFactory sessionFactory;

    public AccountDAOImpl() {
    }

    public AccountDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveAccount(Account account) throws HibernateException {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        session.save(account);
        session.getTransaction().commit();
        System.out.println("Сохранили счет в базу " + account);
    }

    @Override
    public void updateAccNum(String oldAccNum, String newAccNum) throws HibernateException {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        if (this.getAccountByName(oldAccNum) != null) {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query q = session.createQuery("update Account set accNum = :newAccNum" +
                    " where accNum = :oldAccNum");
            q.setParameter("newAccNum", newAccNum);
            q.setParameter("oldAccNum", oldAccNum);
            q.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateSaldo(String accNum, BigDecimal newSaldo) throws HibernateException {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        if (this.getAccountByName(accNum) != null) {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query q = session.createQuery("update Account set saldo = :newSaldo" +
                    " where accNum = :accNum");
            q.setParameter("accNum", accNum);
            q.setParameter("newSaldo", newSaldo);
            q.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateSaldoInTransaction(String accNum, BigDecimal newSaldo, Session session) throws HibernateException {
        Query q = session.createQuery("update Account set saldo = :newSaldo" +
                " where accNum = :accNum");
        q.setParameter("accNum", accNum);
        q.setParameter("newSaldo", newSaldo);
        q.executeUpdate();
    }

    @Override
    public void updateClient(String accNum, Client client) throws HibernateException {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        if (this.getAccountByName(accNum) != null) {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Query q = session.createQuery("update Account set client = :client" +
                    " where accNum = :accNum");
            q.setParameter("client", client);
            q.setParameter("accNum", accNum);
            q.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public Account getAccountById(long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Query q = session.createQuery("from ru.sbertech.Logic.Account where id = :id");
        q.setLong("id", id);
        Account account = (Account) q.uniqueResult();
        session.getTransaction().commit();
        return account;
    }

    @Override
    public Account getAccountByName(String accNum) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Query q = session.createQuery("from ru.sbertech.Logic.Account where accNum = :accNum");
        q.setString("accNum", accNum);
        Account account = (Account) q.uniqueResult();
        session.getTransaction().commit();
        return account;
    }

    @Override
    public List<Account> accountList() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Query query = session.createQuery("from ru.sbertech.Logic.Account");
        List<Account> accounts = query.list();
        session.getTransaction().commit();
        return accounts;
    }

    @Override
    public void removeAccountById(long id) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        Account account = (Account) session.load(Account.class, id);
        if (account != null) {
            session.delete(account);
        }
        session.getTransaction().commit();
    }

    @Override
    public void removeAccountByAccNum(String accNum) throws HibernateException {
        Session session;
        Account account = this.getAccountByName(accNum);
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        session.beginTransaction();
        if (account != null) {
            session.delete(account);
        }
        session.getTransaction().commit();
    }


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
