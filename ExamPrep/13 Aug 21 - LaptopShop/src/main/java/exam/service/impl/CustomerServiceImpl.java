package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Customer;
import exam.model.JsonDto.CustomerSeedDto;
import exam.model.Town;
import exam.repository.CustomerRepository;
import exam.service.CustomerService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String CUSTOMER_INPUT_FILE= "src/main/resources/files/json/customers.json";

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final TownService townService;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, TownService townService) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count()>0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMER_INPUT_FILE));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(this.gson.fromJson(readCustomersFileContent(), CustomerSeedDto[].class))
                .filter(customerSeedDto -> {
                    Customer customer = this.customerRepository.getByEmail(customerSeedDto.getEmail());
                    boolean isValid = validationUtil.isValid(customerSeedDto)&&customer==null;

                    sb.append( isValid ? String.format("Successfully imported Customer %s %s - %s", customerSeedDto.getFirstName(), customerSeedDto.getLastName(), customerSeedDto.getEmail())
                            : "Invalid Customer").append(System.lineSeparator());

                    return isValid;
                })
                .map(customerSeedDto -> {
                    Customer customer = modelMapper.map(customerSeedDto, Customer.class);
                    Town town = townService.getTownByName(customerSeedDto.getTown().getName());
                    customer.setTown(town);
                    return customer;
                })
                .forEach(this.customerRepository::save);
        return sb.toString();
    }
}
