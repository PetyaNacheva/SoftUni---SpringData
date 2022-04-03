package EX2.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "sales")
public class Sale{
    private int id;
    private Product product;
    private Customer Customer;
    private StoreLocation storeLocation;
    private LocalDateTime localDate;

    public Sale() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne
    public Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(Customer customer) {
        Customer = customer;
    }

    @ManyToOne
    public StoreLocation getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(StoreLocation storeLocation) {
        this.storeLocation = storeLocation;
    }
    @Column(name = "date_time")
    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDateTime localDate) {
        this.localDate = localDate;
    }
}
