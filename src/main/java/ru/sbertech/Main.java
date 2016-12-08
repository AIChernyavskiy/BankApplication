package ru.sbertech;


import ru.sbertech.Logic.Account;
import ru.sbertech.Logic.Client;
import ru.sbertech.Logic.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.sbertech.DAO.AccountDAOImpl;
import ru.sbertech.DAO.ClientDaoImpl;
import ru.sbertech.DAO.DocumentDaoImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("contextHibarnate.xml");

    static void printMenu() {
        System.out.println("1-Создать клиента");
        System.out.println("2-Создать счет для клиента");
        System.out.println("3-Создать документ");
        System.out.println("exit-для выхода");
    }

    public static void main(String[] args) {


        Client client = (Client) applicationContext.getBean("ru.sbertech.Logic.Client");
        Client client1 = (Client) applicationContext.getBean("ru.sbertech.Logic.Client");
        Account account = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
        Account account1 = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
        Account accountCT = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
        Account accountDT = (Account) applicationContext.getBean("ru.sbertech.Logic.Account");
        Document document = (Document) applicationContext.getBean("ru.sbertech.Logic.Document");
        ClientDaoImpl clientDao = (ClientDaoImpl) applicationContext.getBean("ClientDaoImpl");
        AccountDAOImpl accountDAO = (AccountDAOImpl) applicationContext.getBean("AccountDAOImpl");
        DocumentDaoImpl documentDao = (DocumentDaoImpl) applicationContext.getBean("DocumentDaoImpl");

        Scanner in = new Scanner(System.in);
        String string;
        printMenu();
//        account.setAccNum("789");
//        account1.setAccNum("101");
//        account.setSaldo(new BigDecimal(100000));
//        account1.setSaldo(new BigDecimal(200000));
//        client.setName("valya");
//        client1.setName("pensya");
//        account.setClient(client);
//        account1.setClient(client1);
//        document.setAccountCT(account);
//        document.setAccountDT(account1);
//        document.setPurpose("naznach");
//        document.setSumma(new BigDecimal(560));
//        document.setDocDate(new Date());
//        documentDao.saveDocument(document);
        clientDao.removeClientByName("valya");
        System.out.println();

//        account1.setClient(clientDao.getClientByName("vasya"));
//        accountDAO.saveAccount(account);
//        accountDAO.saveAccount(account1);
//        accountDAO.updateAccNum("123","321");
//        accountDAO.updateSaldo("456",new BigDecimal(800000));
//        accountDAO.updateClient("321", clientDao.getClientByName("vova"));
//        account = accountDAO.getAccountById(5);
//        account1 = accountDAO.getAccountByName("456");
//        accountDAO.removeAccountByAccNum("123");
        System.out.println();
//        accountDAO.getAccountById(4);
//        accountDAO.accountList();
//        accountDAO.removeAccountById(4);
        System.out.println();
//        accountDAO.removeAccountByAccNum("456");

//        clientDao.saveClient(client);
//        clientDao.saveClient(client1);
//        clientDao.getClientByName("vasya");
//        clientDao.getClientById(8);
//        clientDao.updateClient("vasya","petya");
//        clientDao.clientList();
//        clientDao.removeClientById(8);
//        clientDao.removeClientByName("petya");
//        System.out.println();
//        clientDao.removeClientByName("PETYA");
//        clientDao.removeClientById(1);
        while (!(string = in.nextLine()).equals("exit")) {
            switch (string) {
                case "1":
                    System.out.println("Введите имя клиента");
                    String clientName = in.nextLine();
                    client.setName(clientName);
                    List<Client> clients = clientDao.clientList();
                    clientDao.getClientById(1);

                    if (clientDao.getClientByName(clientName) != null) {
                        System.out.println("Клиент с именем " + clientName + " уже существует, измените имя и попробуйте ещё раз!");
                        clientDao.removeClientByName(clientName);
                    } else {
                        clientDao.saveClient(client);
                    }
                    break;
                case "2":
                    account = accountDAO.getAccountById(1);
                    System.out.println("Введите номер счета ");
                    String accNum = in.nextLine();
                    System.out.println("Введите сальдо");
                    BigDecimal saldo = in.nextBigDecimal();
                    System.out.println("Введите имя клиента");
                    String accNum1 = in.nextLine();
                    String nameClient = in.nextLine();
                    account.setAccNum(accNum);
                    account.setSaldo(saldo);
                    client = clientDao.getClientByName(nameClient);
                    if (client == null) {
                        System.out.println("Клиента с именем " + nameClient + " не существует, создайте сначала клиента");
                    } else {
                        account.setClient(client);
                        accountDAO.saveAccount(account);
                    }
                    break;
                case "3":
                    System.out.println("Введите счет с которого будем снимать средства");
                    String accNumCT = in.nextLine();
                    System.out.println("Введите счет на который будем зачислять средства");
                    String accNumDT = in.nextLine();
                    System.out.println("Введите сумму платежа");
                    BigDecimal summa = in.nextBigDecimal();
                    System.out.println("Введите назначение платежа");
                    in.nextLine();
                    String purpose = in.nextLine();
                    accountCT = accountDAO.getAccountByName(accNumCT);
                    accountDT = accountDAO.getAccountByName(accNumDT);
                    if (accountCT == null) {
                        System.out.println("Счета с номером " + accNumCT + " нет в базе, созлайте счет и попробуйте заново.");
                    } else if (accountDT == null) {
                        System.out.println("Счета с номером " + accNumDT + " нет в базе, созлайте счет и попробуйте заново.");
                    } else if (accountCT.checkSaldo(summa)) {
                        document.setAccountCT(accountCT);
                        document.setAccountDT(accountDT);
                        document.setSumma(summa);
                        //document.setDocDate(new Date());
                        document.setPurpose(purpose);
                        accountCT.saldoAfterTransactionCT(summa);
                        accountDT.saldoAfterTransactionDT(summa);
                        documentDao.saveDocument(document);
                    }
                    break;
                default:
                    System.out.println("Введите 1, 2 или 3 для продолжения или exit для завершения");
            }
            printMenu();
        }
    }
}