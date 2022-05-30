package softuni.exam.models.Dto.XmlDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferRootDto {

    @XmlElement(name = "offer")
    private List<OfferSeedDto> offers;

    public OfferRootDto() {
    }

    public List<OfferSeedDto> getOffers() {
        return offers;
    }

    public void setOffers(List<OfferSeedDto> offers) {
        this.offers = offers;
    }
}
