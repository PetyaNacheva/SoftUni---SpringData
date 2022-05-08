package softuni.exam.models.Entity.XML;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDto {
    @XmlElement(name = "serial-number")
    private String serialNumber;
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "take-off")
    private String takeOff;
    @XmlElement(name = "from-town")
    private TownSeedXmlDto fromTown;
    @XmlElement(name = "to-town")
    private TownSeedXmlDto toTown;
    @XmlElement(name = "passenger")
    private PassengerSeedXmlDto passenger;
    @XmlElement(name = "plane")
    private PlaneSeedXmlDto plane;

    public TicketSeedDto() {
    }
    @Size(min = 2)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    @Min(value = 0)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }



    @NotBlank
    public String getTakeOff() {
        return takeOff;
    }

    public void setTakeOff(String takeOff) {
        this.takeOff = takeOff;
    }
    @NotNull
    public TownSeedXmlDto getFromTown() {
        return fromTown;
    }

    public void setFromTown(TownSeedXmlDto fromTown) {
        this.fromTown = fromTown;
    }
    @NotNull
    public TownSeedXmlDto getToTown() {
        return toTown;
    }

    public void setToTown(TownSeedXmlDto toTown) {
        this.toTown = toTown;
    }
    @NotNull
    public PassengerSeedXmlDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerSeedXmlDto passenger) {
        this.passenger = passenger;
    }
    @NotNull
    public PlaneSeedXmlDto getPlane() {
        return plane;
    }

    public void setPlane(PlaneSeedXmlDto plane) {
        this.plane = plane;
    }
}
