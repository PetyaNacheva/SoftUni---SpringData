package hiberspring.domain.entities;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "employee_cards")
public class EmployeeCard extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String number;
    @Autowired
    public EmployeeCard() {

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
