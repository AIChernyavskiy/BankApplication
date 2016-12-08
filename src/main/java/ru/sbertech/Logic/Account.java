package ru.sbertech.Logic;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;


@Entity
@Table(name = "ACCOUNTS")
public class Account implements Serializable {

    @Id
    @Column(name = "ID_ACCOUNT")
    @GeneratedValue
    long id;

    @Column(name = "SALDO", nullable = false)
    BigDecimal saldo;

    @Column(name = "ACCNUM", nullable = false)
    String accNum;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "CLIENT_ID")
    Client client;

    @JsonIgnore
    @OneToMany(mappedBy = "AccountCT", fetch = FetchType.LAZY)
    @Cascade({CascadeType.DELETE})
    private Set<Document> documentsCT;

    @JsonIgnore
    @OneToMany(mappedBy = "AccountDT", fetch = FetchType.LAZY)
    @Cascade({CascadeType.DELETE})
    private Set<Document> documentsDT;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean checkSaldo(BigDecimal summa) {
        int equals = saldo.compareTo(summa);
        if (equals >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void saldoAfterTransactionCT(BigDecimal summa) {
        saldo = saldo.subtract(summa);
    }

    public void saldoAfterTransactionDT(BigDecimal summa) {
        saldo = saldo.add(summa);
    }

    public Set<Document> getDocumentsCT() {
        return documentsCT;
    }

    public void setDocumentsCT(Set<Document> documentsCT) {
        this.documentsCT = documentsCT;
    }

    public Set<Document> getDocumentsDT() {
        return documentsDT;
    }

    public void setDocumentsDT(Set<Document> documentsDT) {
        this.documentsDT = documentsDT;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "ru.sbertech.Logic.Account{" +
                "id=" + id +
                ", saldo=" + saldo +
                ", accNum='" + accNum + '\'' +
                ", client=" + client +
                '}';
    }
}
