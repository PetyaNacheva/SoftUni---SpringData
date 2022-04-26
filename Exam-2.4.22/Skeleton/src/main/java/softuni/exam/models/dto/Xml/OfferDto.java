package softuni.exam.models.dto.Xml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)

public class OfferDto {
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "agent")
    private AgentXmlDto agent;
    @XmlElement(name = "apartment")
    private ApartmentXmlDto apartment;
    @XmlElement(name = "publishedOn")
    private String publishedOn;

    public OfferDto() {
    }
    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public AgentXmlDto getAgent() {
        return agent;
    }

    public void setAgent(AgentXmlDto agent) {
        this.agent = agent;
    }

    public ApartmentXmlDto getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentXmlDto apartment) {
        this.apartment = apartment;
    }
    @NotBlank
    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }
}
