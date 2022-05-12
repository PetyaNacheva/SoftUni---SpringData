package exam.model.XMLDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TownNameDto {
    @XmlElement(name = "town")
    private String town;

    public TownNameDto() {
    }
    @Size(min = 2)

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
