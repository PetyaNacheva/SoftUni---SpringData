package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Xml.ApartmentRootDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private static final String APARTMENT_FILE_PATH = "C:\\Users\\Pepi\\Desktop\\Java DB Advanced - Exam 2.4.22\\skeleton\\src\\main\\resources\\files\\xml\\apartments.xml";

   private final ApartmentRepository apartmentRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final TownService townService;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, TownService townService) {
        this.apartmentRepository = apartmentRepository;

        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count()>0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENT_FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
       // ApartmentRootDto apartmentRootDto = xmlParser.parseXml(APARTMENT_FILE_PATH, ApartmentRootDto.class);
        List<String> types= Arrays.stream(ApartmentType.values()).map(v->v.toString().toLowerCase(Locale.ROOT)).collect(Collectors.toList());
        xmlParser.parseXml(APARTMENT_FILE_PATH, ApartmentRootDto.class).getApartment()
                    .stream().filter(apartmentDto -> {
                        Town town = this.townService.findTownByName(apartmentDto.getTown());
                        Apartment apartment = this.apartmentRepository.findApartmentByTownAndArea(town, apartmentDto.getArea());
                        boolean isValid = validationUtil.isValid(apartmentDto)&&apartment==null;
                    if(!types.contains(apartmentDto.getApartmentType())|| apartmentDto.getApartmentType().isEmpty()){
                        isValid = false;
                    }
                        sb.append(isValid ? String.format("Successfully imported apartment %s - %.2f", apartmentDto.getApartmentType(), apartmentDto.getArea())
                                : "Invalid apartment").append(System.lineSeparator());

                        return isValid;
                    }).map(apartmentDto -> {
                        Apartment apartment = modelMapper.map(apartmentDto, Apartment.class);
                    for (String type : types) {
                        if(apartmentDto.getApartmentType().equals(type)){
                         apartment.setApartmentType(ApartmentType.valueOf(type));
                        }
                    }
                        Town town = this.townService.findTownByName(apartmentDto.getTown());
                        apartment.setTown(town);

                        return apartment;
                    }).forEach(this.apartmentRepository::save);


        return sb.toString();
    }

    @Override
    public Apartment findApartmentById(Long id) {
        return this.apartmentRepository.findApartmentById(id);
    }
}
