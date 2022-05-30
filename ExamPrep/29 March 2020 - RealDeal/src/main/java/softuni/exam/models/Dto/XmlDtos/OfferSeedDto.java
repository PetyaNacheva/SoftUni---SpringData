package softuni.exam.models.Dto.XmlDtos;

import org.hibernate.validator.constraints.Length;
import softuni.exam.models.Car;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDto {
    @XmlElement
    private String description;
    @XmlElement
    private BigDecimal price;
    @XmlElement(name = "added-on")
    private String addedOn;
    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;
    @XmlElement
    private CarSeedXmlDto car;
    @XmlElement
    private SellerSeedXmlDto seller;


    public OfferSeedDto() {
    }
    @Length(min = 5)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @DecimalMin(value = "0")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public CarSeedXmlDto getCar() {
        return car;
    }

    public void setCar(CarSeedXmlDto car) {
        this.car = car;
    }

    public SellerSeedXmlDto getSeller() {
        return seller;
    }

    public void setSeller(SellerSeedXmlDto seller) {
        this.seller = seller;
    }
}
