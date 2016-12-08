package ru.sbertech.Logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "CLIENTS")
public class Client implements Serializable {

    public Client() {
    }

    @Id
    @Column(name = "ID_CLIENT")
    @GeneratedValue
    long id;

    @Column(name = "NAME", nullable = false)
    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    @Cascade({CascadeType.DELETE})
    private Set<Account> accounts;

    @Override
    public String toString() {
        return "ru.sbertech.Logic.Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

}
