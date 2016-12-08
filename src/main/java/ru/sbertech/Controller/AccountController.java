package ru.sbertech.Controller;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.sbertech.DAO.AccountDAOImpl;
import ru.sbertech.DAO.ClientDaoImpl;
import ru.sbertech.Logic.Account;
import ru.sbertech.Logic.Client;


import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;

import static ru.sbertech.Main.applicationContext;

@Controller
public class AccountController {

    public AccountController() {
    }

    ClientDaoImpl clientDao = (ClientDaoImpl) applicationContext.getBean("ClientDaoImpl");
    AccountDAOImpl accountDAO = (AccountDAOImpl) applicationContext.getBean("AccountDAOImpl");
    public static final String PAGE_ACCOUNT = "Account";


    @RequestMapping(value = {"/Account"}, method = RequestMethod.GET)
    public ModelAndView frontPageAccount() {
        return new ModelAndView(PAGE_ACCOUNT);
    }

    @RequestMapping(value = {"/Account/CreateAccount"}, method = RequestMethod.GET)
    public ModelAndView createAccount(HttpServletRequest request) {
        return new ModelAndView(PAGE_ACCOUNT, "messageCreateAccount", checkExistenceClientAndSaveAccount(request));
    }

    @RequestMapping(value = {"/Account/CreateAccountAndClient"}, method = RequestMethod.GET)
    public ModelAndView createAccountAndClient(HttpServletRequest request) {
        return new ModelAndView(PAGE_ACCOUNT, "messageCreateAccountAndClient", checkExistenceClientAndSaveAccountWithClient(request));
    }

    @RequestMapping(value = {"/Account/Delete"}, method = RequestMethod.GET)
    public ModelAndView deleteAccount(HttpServletRequest request) {
        return new ModelAndView(PAGE_ACCOUNT, "messageDelete", checkExistenceAccountAndDeleteAccount(request));
    }

    @RequestMapping(value = {"/Account/UpdateNumber"}, method = RequestMethod.GET)
    public ModelAndView updateAccountNumber(HttpServletRequest request) {
        return new ModelAndView(PAGE_ACCOUNT, "messageUpdateNumber", findAccountAndUpdateAccNum(request));
    }

    @RequestMapping(value = {"/Account/UpdateSaldo"}, method = RequestMethod.GET)
    public ModelAndView updateAccountSaldo(HttpServletRequest request) {
        return new ModelAndView(PAGE_ACCOUNT, "messageUpdateSaldo", findAccountAndUpdateSaldo(request));
    }

    @RequestMapping(value = {"/Account/UpdateClient"}, method = RequestMethod.GET)
    public ModelAndView updateAccountClient(HttpServletRequest request) {
        return new ModelAndView(PAGE_ACCOUNT, "messageUpdateClient", findAccountAndUpdateClient(request));
    }

    @RequestMapping(value = {"/Account/Find"}, method = RequestMethod.GET)
    public ModelAndView findAccount(HttpServletRequest request) {
        return new ModelAndView(PAGE_ACCOUNT, "messageFind", checkAndfindAccount(request));
    }

    @RequestMapping(value = {"/PrintAccounts"}, method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Account> printClients() {
        ArrayList<Account> accounts = (ArrayList<Account>) accountDAO.accountList();
        return accounts;
    }

    String checkExistenceClientAndSaveAccount(HttpServletRequest request) {
        Account account = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
        Client client = (Client) applicationContext.getBean("ru.sbertech.Logic.Client");
        client = clientDao.getClientByName(request.getParameter("clientName"));
        account = accountDAO.getAccountByName(request.getParameter("accNum"));
        if (account != null) {
            return "Account " + account.getAccNum() + " already exsist";
        } else if (client != null) {
            Account account1 = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
            account1.setAccNum(request.getParameter("accNum"));
            account1.setSaldo(BigDecimal.valueOf(Long.parseLong(request.getParameter("saldo"))));
            account1.setClient(client);
            try {
                accountDAO.saveAccount(account1);
                return "The account " + account1.getAccNum() + " has been created";
            } catch (HibernateException e) {
                return "Error " + e;
            }
        } else {
            return "Client's name " + request.getParameter("clientName") + " does not exist ";
        }
    }

    String checkExistenceClientAndSaveAccountWithClient(HttpServletRequest request) {
        Account account = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
        Client client = (Client) applicationContext.getBean("ru.sbertech.Logic.Client");
        client = clientDao.getClientByName(request.getParameter("clientName"));
        account = accountDAO.getAccountByName(request.getParameter("accNum"));
        if (account != null) {
            return "Account " + account.getAccNum() + " already exsist";
        } else if (client != null) {
            return "Client " + client.getName() + " already exsist";
        } else {
            Account account1 = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
            Client client1 = (Client) applicationContext.getBean("ru.sbertech.Logic.Client");
            client1.setName(request.getParameter("clientName"));
            account1.setAccNum(request.getParameter("accNum"));
            account1.setSaldo(BigDecimal.valueOf(Long.parseLong(request.getParameter("saldo"))));
            account1.setClient(client1);
            try {
                accountDAO.saveAccount(account1);
                return "The account " + account1.getAccNum() + " and client has been created";
            } catch (HibernateException e) {
                return "Error " + e;
            }
        }
    }

    String checkExistenceAccountAndDeleteAccount(HttpServletRequest request) {
        Account account = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
        account = accountDAO.getAccountByName(request.getParameter("accNum"));
        if (account != null) {
            try {
                accountDAO.removeAccountByAccNum(account.getAccNum());
            } catch (HibernateException e) {
                return "Error " + e;
            }
            return "Account " + account.getAccNum() + " has been removed";
        } else {
            return "Account " + request.getParameter("accNum") + " does not exist";
        }
    }

    String checkAndfindAccount(HttpServletRequest request) {
        Account account = accountDAO.getAccountByName(request.getParameter("accNum"));
        if (account != null) {
            return "ID " + account.getId() + " ACCNUM " + account.getAccNum() + " SALDO " + account.getSaldo() +
                    " ID_CLIENT " + account.getClient().getId() + " CLIENT NAME " + account.getClient().getName();
        } else {
            return "The database is not a account with number " + request.getParameter("accNum");
        }
    }

    String findAccountAndUpdateAccNum(HttpServletRequest request) {
        Account account = accountDAO.getAccountByName(request.getParameter("oldAccNum"));
        Account account1 = accountDAO.getAccountByName(request.getParameter("newAccNum"));
        if ((account != null) & (account1 == null)) {
            try {
                accountDAO.updateAccNum(request.getParameter("oldAccNum"), request.getParameter("newAccNum"));
                return "Update account number " + account.getAccNum() + " on " + request.getParameter("newAccNum");
            } catch (HibernateException e) {
                return "Error " + e;
            }
        } else if (account == null) {
            return "The database is not a account with number " + request.getParameter("oldAccNum");
        } else {
            return "Account " + account1.getAccNum() + " already exsist";
        }
    }

    String findAccountAndUpdateSaldo(HttpServletRequest request) {
        Account account = accountDAO.getAccountByName(request.getParameter("accNum"));
        if (account != null) {
            try {
                accountDAO.updateSaldo(account.getAccNum(), BigDecimal.valueOf(Long.parseLong(request.getParameter("newSaldo"))));
                return "Update account saldo " + account.getSaldo() + " on " + request.getParameter("newSaldo");
            } catch (HibernateException e) {
                return "Error " + e;
            }
        } else {
            return "The database is not a account with number " + request.getParameter("accNum");
        }
    }

    String findAccountAndUpdateClient(HttpServletRequest request) {
        Account account = accountDAO.getAccountByName(request.getParameter("accNum"));
        Client client = clientDao.getClientByName(request.getParameter("clientName"));
        if ((account != null) && (client != null)) {
            try {
                accountDAO.updateClient(account.getAccNum(), client);
                return "Update account client " + account.getClient() + " on " + request.getParameter("clientName");
            } catch (HibernateException e) {
                return "Error " + e;
            }
        } else if (client == null) {
            return "Client's name " + request.getParameter("clientName") + " does not exist ";
        } else {
            return "The database is not a account with number " + request.getParameter("accNum");
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
}
