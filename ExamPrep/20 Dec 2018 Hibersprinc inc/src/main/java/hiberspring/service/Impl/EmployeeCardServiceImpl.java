package hiberspring.service.Impl;

import com.google.gson.Gson;
import hiberspring.domain.dtos.Json.EmployeeCardsSeedDto;
import hiberspring.domain.dtos.Json.TownSeedDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.domain.entities.Town;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
    private static final String EMPLOOECARD_FILE_PATH = "src/main/resources/files/employee-cards.json";
    
    private final EmployeeCardRepository employeeCardRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;


    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.employeeCardRepository = employeeCardRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count()>0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(EMPLOOECARD_FILE_PATH));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readEmployeeCardsJsonFile(), EmployeeCardsSeedDto[].class))
                .filter(employeeCardsSeedDto -> {
                    EmployeeCard card = this.employeeCardRepository.findEmployeeCardByNumber(employeeCardsSeedDto.getNumber());
                    boolean isValid = validationUtil.isValid(employeeCardsSeedDto)&&card==null;

                    sb.append(isValid ? String.format("Successfully imported Employee Card %s.", employeeCardsSeedDto.getNumber()) : "Error: Invalid data.")
                            .append(System.lineSeparator());
                    return isValid;
                }).map(employeeCardsSeedDto-> modelMapper.map(employeeCardsSeedDto,EmployeeCard.class))
                .forEach(this.employeeCardRepository::saveAndFlush);

        return sb.toString();
    }

    @Override
    public EmployeeCard findCardByNumber(String card) {
        return this.employeeCardRepository.findEmployeeCardByNumber(card);
    }
}
