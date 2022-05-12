package exam.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cutomers")
public class Customer extends BaseEntity{
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate registeredOn;
    private Town town;

    public Customer() {
    }
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "registered_On", nullable = false)
    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }
    @ManyToOne(fetch = FetchType.EAGER)
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }
}
