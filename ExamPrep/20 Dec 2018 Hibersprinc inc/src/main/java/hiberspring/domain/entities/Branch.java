package hiberspring.domain.entities;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "branches")
public class Branch extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @ManyToOne(optional = false)
    private Town town;
    @OneToMany
    private Set<Product> products;

    @Autowired
    public Branch() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
