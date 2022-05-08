package softuni.exam.models.Entity.XML;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketRootSeedDto {
    @XmlElement(name = "ticket")
    private List<TicketSeedDto> tickets;

    public TicketRootSeedDto() {
    }

    public List<TicketSeedDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketSeedDto> tickets) {
        this.tickets = tickets;
    }
}
