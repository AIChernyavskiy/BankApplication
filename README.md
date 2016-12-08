# BankApplication
Необходимо реализовать два мини банковских приложения.
Первое приложение:
сущности:
 - клиент, счет, документ.

class Client
	long id;
	String name;
class Account
	long id;
	BigDecimal saldo;
	String accNum;
	Client client;
class Document
	long id;
	Account accDT;
	Account accCT;
	BigDecimal summa;
	String purpose;
	Date docDate;

Для каждой сущности реализовать возможность CRUD операций.
Document осуществляет списание и зачисление средств (проводка).
Используемые технологии:
Maven, Tomcat, Mockito, JUnit, Spring, Hibernate, JMS, HTML, CSS, JavaScript, JQuery, JSON, XML, Sonar.
