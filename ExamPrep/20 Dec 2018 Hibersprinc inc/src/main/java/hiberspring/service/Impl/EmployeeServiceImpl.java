package hiberspring.service.Impl;

import hiberspring.domain.dtos.Xml.EmployeeRootSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeRepository;
import hiberspring.service.BranchService;
import hiberspring.service.EmployeeCardService;
import hiberspring.service.EmployeeService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String EMPLOYEE_FILE_PATH = "src/main/resources/files/employees.xml";

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final BranchService branchService;
    private final EmployeeCardService employeeCardService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, BranchService branchService, EmployeeCardService employeeCardService) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.branchService = branchService;
        this.employeeCardService = employeeCardService;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count()>0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(EMPLOYEE_FILE_PATH));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        //EmployeeRootSeedDto employeeRootSeedDto = xmlParser.parseXml(EMPLOYEE_FILE_PATH, EmployeeRootSeedDto.class);
           xmlParser.parseXml(EMPLOYEE_FILE_PATH, EmployeeRootSeedDto.class).getEmployees()
                    .stream().filter(employeeSeedDto -> {
                       EmployeeCard card = this.employeeCardService.findCardByNumber(employeeSeedDto.getCard());
                       Branch branch = this.branchService.findBranchByName(employeeSeedDto.getBranch());
                       Employee employee = this.employeeRepository.findEmployeeByCardNumber(employeeSeedDto.getCard());
                        boolean isValid = validationUtil.isValid(employeeSeedDto)&&card!=null&&branch!=null&&employee==null;

                        sb.append(isValid ? String.format("Successfully imported Employee %s %s.", employeeSeedDto.getFirstName(), employeeSeedDto.getLastName())
                                : "Error: Invalid data.").append(System.lineSeparator());
                        return isValid;
                    }).map(employeeSeedDto -> {
                        Employee employee = modelMapper.map(employeeSeedDto, Employee.class);
                        EmployeeCard card = this.employeeCardService.findCardByNumber(employeeSeedDto.getCard());
                        Branch branch = this.branchService.findBranchByName(employeeSeedDto.getBranch());

                        employee.setBranch(branch);
                        employee.setCard(card);
                        return employee;
                    }).forEach(this.employeeRepository::saveAndFlush);


        return sb.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        return null;
    }
}
