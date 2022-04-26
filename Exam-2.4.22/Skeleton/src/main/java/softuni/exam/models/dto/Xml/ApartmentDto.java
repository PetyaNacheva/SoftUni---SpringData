package softuni.exam.models.dto.Xml;

import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentDto {
    @XmlElement(name = "apartmentType")
    private String apartmentType;
    @XmlElement(name = "area")
    private double area;
    @XmlElement(name = "town")
    private String town;

    public ApartmentDto() {
    }
    @Enumerated
    public String getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(String apartmentType) {
        this.apartmentType = apartmentType;
    }
    @DecimalMin(value = "40.00")
    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
    @NotNull
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
