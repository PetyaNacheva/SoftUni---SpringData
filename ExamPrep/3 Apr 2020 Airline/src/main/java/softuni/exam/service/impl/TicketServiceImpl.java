package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Entity.Passenger;
import softuni.exam.models.Entity.Plane;
import softuni.exam.models.Entity.Ticket;
import softuni.exam.models.Entity.Town;
import softuni.exam.models.Entity.XML.TicketRootSeedDto;
import softuni.exam.models.Entity.XML.TicketSeedDto;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.PlaneService;
import softuni.exam.service.TicketService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TicketServiceImpl implements TicketService {
    private static final String TICKET_INPUT_FILE = "src/main/resources/files/xml/tickets.xml";
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final PlaneService planeService;
    private final TownService townService;
    private final PassengerService passengerService;

    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, PlaneService planeService, TownService townService, PassengerService passengerService) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.planeService = planeService;
        this.townService = townService;
        this.passengerService = passengerService;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count()>0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKET_INPUT_FILE));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
       // TicketRootSeedDto TicketRootSeedDto = xmlParser.parseXml(TICKET_INPUT_FILE, TicketRootSeedDto.class);

     xmlParser.parseXml(TICKET_INPUT_FILE, TicketRootSeedDto.class).getTickets().stream()
                .filter(ticketSeedDto -> {
                    boolean isValid = validationUtil.isValid(ticketSeedDto);

                    sb.append(isValid ? String.format("Successfully imported Ticket %s - %s", ticketSeedDto.getFromTown(), ticketSeedDto.getToTown())
                            : "Invalid Ticket").append(System.lineSeparator());

                    return isValid;
                }).map(ticketSeedDto -> {
                    Ticket ticket = modelMapper.map(ticketSeedDto, Ticket.class);
                    Plane plane = this.planeService.findByRegNumber(ticketSeedDto.getPlane().getRegisterNumber());
                    ticket.setPlane(plane);
                    Town townFrom = this.townService.findByName(ticketSeedDto.getFromTown().getName());
                    Town townTo = this.townService.findByName(ticketSeedDto.getToTown().getName());
                    Passenger passenger = this.passengerService.findByEmail(ticketSeedDto.getPassenger().getEmail());
                    ticket.setFromTown(townFrom);
                    ticket.setToTown(townTo);
                    ticket.setPassenger(passenger);

                    return ticket;
                }).forEach(this.ticketRepository::save);
        /*for (TicketSeedDto ticket : TicketRootSeedDto.getTickets()) {
            Ticket ticket1 = this.modelMapper.map(ticket,Ticket.class);
            String tt = ticket.getFromTown().getName();
            Town fromTown = this.townService.findByName(tt);
            Town toTown = this.townService.findByName(ticket.getToTown().getName());
            Passenger passenger = this.passengerService.findByEmail(ticket.getPassenger().getEmail());
            Plane plane = this.planeService.findByRegNumber(ticket.getPlane().getRegisterNumber());

            if (validationUtil.isValid(ticket) && validationUtil.isValid(ticket.getFromTown().getName()) && validationUtil.isValid(ticket.getToTown().getName()) &&
                    validationUtil.isValid(ticket.getPassenger().getEmail()) && validationUtil.isValid(ticket.getPlane().getRegisterNumber())){
                ticket1.setFromTown(fromTown);
                ticket1.setToTown(toTown);
                ticket1.setPassenger(passenger);
                ticket1.setPlane(plane);

                this.ticketRepository.saveAndFlush(ticket1);

                sb.append(String.format("Successfully imported Ticket %s - %s",ticket1.getFromTown().getName(),ticket1.getToTown().getName()))
                        .append(System.lineSeparator());

            }else {
                sb.append("Invalid Ticket")
                        .append(System.lineSeparator());
            }
        }*/

        return sb.toString();
    }
}
