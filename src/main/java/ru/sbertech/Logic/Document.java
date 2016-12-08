package ru.sbertech.Logic;


import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "DOCUMENTS")
public class Document implements Serializable {

    @Id
    @Column(name = "ID_DOCUMENT")
    @GeneratedValue
    long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "ACCDT")
    Account AccountDT;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "ACCCT")
    Account AccountCT;

    @Column(name = "SUMMA", nullable = false)
    BigDecimal summa;

    @Column(name = "PURPOSE")
    String purpose;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DOCDATE", nullable = false)
    Date docDate;

    @Column(name = "STORNO", nullable = false)
    boolean storno;

    @Override
    public String toString() {
        return "ru.sbertech.Logic.Document{" +
                "id=" + id +
                ", accDT=" + AccountDT +
                ", accCT=" + AccountCT +
                ", summa=" + summa +
                ", purpose='" + purpose + '\'' +
                ", docDate=" + docDate +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccountDT() {
        return AccountDT;
    }

    public void setAccountDT(Account AccountDT) {
        this.AccountDT = AccountDT;
    }

    public Account getAccountCT() {
        return AccountCT;
    }

    public void setAccountCT(Account AccountCT) {
        this.AccountCT = AccountCT;
    }

    public BigDecimal getSumma() {
        return summa;
    }

    public void setSumma(BigDecimal summa) {
        this.summa = summa;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public boolean isStorno() {
        return storno;
    }

    public void setStorno(boolean storno) {
        this.storno = storno;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
