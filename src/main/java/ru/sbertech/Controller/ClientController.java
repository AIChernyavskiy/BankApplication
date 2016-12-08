package ru.sbertech.Controller;


import org.hibernate.HibernateException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.sbertech.DAO.ClientDaoImpl;
import ru.sbertech.Logic.Client;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import static ru.sbertech.Main.applicationContext;

@Controller
public class ClientController {

    ClientDaoImpl clientDao = (ClientDaoImpl) applicationContext.getBean("ClientDaoImpl");
    public static final String PAGE_CLIENT = "Client";
    public ClientController() {

    }


    @RequestMapping(value = {"/Client"}, method = RequestMethod.GET)
    public ModelAndView frontPageClient() {
        return new ModelAndView(PAGE_CLIENT);
    }



    @RequestMapping(value = {"/Client/Create"}, method = RequestMethod.GET)
    public ModelAndView createClient(Client client) {
        return new ModelAndView(PAGE_CLIENT, "messageCreate", checkExistenceClientAndSave(client));
    }

    @RequestMapping(value = {"/Client/Update"}, method = RequestMethod.GET)
    public ModelAndView updateClient(HttpServletRequest request) {
        String oldName = request.getParameter("oldName");
        String newName = request.getParameter("newName");
        return new ModelAndView(PAGE_CLIENT, "messageUpdate", checkExistenceClientAndRename(oldName, newName));
    }

    @RequestMapping(value = {"/Client/Delete"}, method = RequestMethod.GET)
    public ModelAndView deleteClient(Client client) {
        return new ModelAndView(PAGE_CLIENT, "messageDelete", checkExistenceClientAndDelete(client));
    }

    @RequestMapping(value = {"/Client/Find"}, method = RequestMethod.GET)
    public ModelAndView findClient(Client client) {
        return new ModelAndView(PAGE_CLIENT, "messageFind", checkExistenceClient(client));
    }

    @RequestMapping(value = {"/PrintClients"}, method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Client> printClients() {
        ArrayList<Client> clients = (ArrayList<Client>) clientDao.clientList();
        return clients;
    }


    public String checkExistenceClientAndSave(Client client) {
        if (clientDao.getClientByName(client.getName()) != null) {
            return "The client " + client.getName() + " already exists, change the name and try again";
        } else {
            try {
                clientDao.saveClient(client);
                return "The client " + client.getName() + " has been created";
            } catch (HibernateException e) {
                return "Error " + e;
            }
        }
    }

    String checkExistenceClientAndDelete(Client client) {
        if (clientDao.getClientByName(client.getName()) != null) {
            try {
                clientDao.removeClientByName(client.getName());
                return "The client " + client.getName() + " was removed";
            } catch (HibernateException e) {
                return "Error " + e;
            }

        } else {
            return "The database is not a client with name " + client.getName();
        }
    }

    String checkExistenceClientAndRename(String oldName, String newName) {
        if ((clientDao.getClientByName(oldName) != null) & (clientDao.getClientByName(newName) == null)) {
            try {
                clientDao.updateClient(oldName, newName);
                return "The client " + oldName + " was renamed on " + newName;
            } catch (HibernateException e) {
                return "Error " + e;
            }

        } else if (clientDao.getClientByName(oldName) == null) {
            return "The database is not a client with name " + oldName;
        } else {
            return "The client " + newName + " already exists, change the name and try again";
        }
    }

    String checkExistenceClient(Client client) {
        Client checkClient = clientDao.getClientByName(client.getName());
        if (checkClient!= null) {
            return "ID " + checkClient.getId() + " NAME " + checkClient.getName();
        } else {
            return "The database is not a client with name "+ client.getName();
        }
    }

    public ClientDaoImpl getClientDao() {
        return clientDao;
    }

    public void setClientDao(ClientDaoImpl clientDao) {
        this.clientDao = clientDao;
    }
}