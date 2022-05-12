package exam.model.XMLDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "towns")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownSeedRootDto {
    @XmlElement(name = "town")
    private List<TownSeedDto> town;

    public TownSeedRootDto() {
    }

    public List<TownSeedDto> getTown() {
        return town;
    }

    public void setTown(List<TownSeedDto> town) {
        this.town = town;
    }
}
