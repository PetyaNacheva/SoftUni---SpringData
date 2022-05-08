package softuni.exam.models.Entity.XML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "planes")
public class PlaneRootSeedDto {
    @XmlElement(name = "plane")
    private List<PlaneSeedDto> planes;

    public PlaneRootSeedDto() {
    }

    public List<PlaneSeedDto> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlaneSeedDto> planes) {
        this.planes = planes;
    }
}
