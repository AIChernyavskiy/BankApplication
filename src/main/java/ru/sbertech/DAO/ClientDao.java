package ru.sbertech.DAO;


import ru.sbertech.Logic.Client;

import java.util.List;

public interface ClientDao {

    void saveClient(Client client);

    void updateClient(String oldName, String newName);

    List<Client> clientList();

    void removeClientById(long id);

    void removeClientByName(String name);

    Client getClientById(long id);

    Client getClientByName(String name);
}
