package softuni.exam.models.dto.Xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentRootDto {
    @XmlElement(name = "apartment")
    List<ApartmentDto> apartment;

    public ApartmentRootDto() {
    }

    public List<ApartmentDto> getApartment() {
        return apartment;
    }

    public void setApartment(List<ApartmentDto> apartment) {
        this.apartment = apartment;
    }
}
