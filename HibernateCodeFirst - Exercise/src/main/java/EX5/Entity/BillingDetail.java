package EX5.Entity;

import javax.persistence.*;


@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public abstract class BillingDetail extends BaseEntity {
    private String number;

    private User owner;
    public BillingDetail() {
    }

    @Column(name = "card_number", nullable = false, unique = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }





}
