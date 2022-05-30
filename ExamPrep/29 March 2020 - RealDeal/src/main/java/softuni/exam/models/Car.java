package softuni.exam.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    private String make;
    private String model;
    private Integer kilometers;
    private LocalDate registeredOn;
    private Set<Picture>pictures;


    public Car() {
    }
    @Column(nullable = false)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @Column(nullable = false)
    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }
    @Column
    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }
}
