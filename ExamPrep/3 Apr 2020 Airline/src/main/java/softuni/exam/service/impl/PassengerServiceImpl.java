package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Entity.Json.PassengerSeedDto;
import softuni.exam.models.Entity.Json.PassengersByTicketsDto;
import softuni.exam.models.Entity.Passenger;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class PassengerServiceImpl implements PassengerService {
    private static final String PASSENGER_FILE_PATH = "src/main/resources/files/json/passengers.json";
    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;


    public PassengerServiceImpl(PassengerRepository passengerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.passengerRepository = passengerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count()>0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGER_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder sb = new StringBuilder();
        //PassengerSeedDto [] passengerSeedDtos = gson.fromJson(readPassengersFileContent(), PassengerSeedDto[].class);
        Arrays.stream(gson.fromJson(readPassengersFileContent(), PassengerSeedDto[].class))
                .filter(passengerSeedDto -> {
                    boolean isValid = validationUtil.isValid(passengerSeedDto);

                    sb.append( isValid ? String.format("Successfully imported Passenger %s - %s", passengerSeedDto.getLastName(), passengerSeedDto.getEmail())
                            : "Invalid Passenger").append(System.lineSeparator());

                    return isValid;
                }).map(passengerSeedDto -> modelMapper.map(passengerSeedDto, Passenger.class))
                .forEach(this.passengerRepository::save);

        return sb.toString();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        StringBuilder sb = new StringBuilder();
       /* List<Object[]> passengers =this.passengerRepository.finAllByTicketsCountInDescOrderThenByEmail();
        List<PassengersByTicketsDto> passengersByTicketsDtos =new ArrayList<>();
        for (Object[] passenger : passengers) {

            PassengersByTicketsDto passByTickets = new PassengersByTicketsDto();
            passByTickets.setFirstName((String) passenger[0]);
            passByTickets.setLastName((String) passenger[1]);
            passByTickets.setEmail((String) passenger[2]);
            passByTickets.setPhoneNumber((String) passenger[3]);
            passByTickets.setNumberOfTicket((int) passenger[4]);

            passengersByTicketsDtos.add(passByTickets);
        }
        passengersByTicketsDtos.stream().forEach(pass->{
        sb.append(String.format("Passenger %s  %s", pass.getFirstName(), pass.getLastName())).append(System.lineSeparator());
        sb.append(String.format("Email - %s",pass.getEmail())).append(System.lineSeparator());
        sb.append(String.format("Phone - %s", pass.getPhoneNumber())).append(System.lineSeparator());
        sb.append(String.format("Number of tickets - %d",pass.getNumberOfTicket())).append(System.lineSeparator());
        sb.append(System.lineSeparator());
        });
        sb.append(System.lineSeparator());
        return sb.toString();*/
        StringBuilder builder = new StringBuilder();

        Set<Passenger> passengers = this.passengerRepository.findAllOrderByTicketCountAndEmail();
        for (Passenger passenger : passengers) {
            builder.append(String.format("Passenger %s  %s%n" +
                            "\tEmail - %s%n" +
                            "\tPhone - %s%n" +
                            "\tNumber of tickets - %d%n",
                    passenger.getFirstName(),passenger.getLastName(),
                    passenger.getEmail(),
                    passenger.getPhoneNumber(),
                    passenger.getTickets().size()));
            builder.append(System.lineSeparator());
        }

        return builder.toString();

    }

    @Override
    public Passenger findByEmail(String email) {
        return this.passengerRepository.findPassengerByEmail(email);
    }
}
