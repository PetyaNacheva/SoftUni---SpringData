package hiberspring.domain.entities;

import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int clients;
    @ManyToOne
    private Branch branch;
    @Autowired
    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClients() {
        return clients;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }


    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
