package ru.sbertech.DAO;


import org.hibernate.Session;
import ru.sbertech.Logic.Account;
import ru.sbertech.Logic.Client;


import java.math.BigDecimal;
import java.util.List;

public interface AccountDAO {

    void saveAccount(Account account);

    void updateAccNum(String oldAccNum, String newAccNum);

    void updateSaldo(String accNum, BigDecimal newSaldo);

    void updateSaldoInTransaction(String accNum, BigDecimal newSaldo, Session session);

    void updateClient(String accNum, Client client);

    List<Account> accountList();

    void removeAccountById(long id);

    void removeAccountByAccNum(String accNum);

    Account getAccountById(long id);

    Account getAccountByName(String accNum);
}
