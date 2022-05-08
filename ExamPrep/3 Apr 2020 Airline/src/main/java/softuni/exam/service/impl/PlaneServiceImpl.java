package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Entity.Plane;
import softuni.exam.models.Entity.XML.PlaneRootSeedDto;
import softuni.exam.models.Entity.XML.PlaneSeedDto;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PlaneServiceImpl implements PlaneService {
    private static final String PLANE_INPUT_FILE = "src/main/resources/files/xml/planes.xml";
    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PlaneServiceImpl(PlaneRepository planeRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count()>0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PLANE_INPUT_FILE));
    }

    @Override
    public String importPlanes() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
      // List<PlaneSeedDto> planeRootSeedDto = xmlParser.parseXml(PLANE_INPUT_FILE, PlaneRootSeedDto.class).getPlanes();
       xmlParser.parseXml(PLANE_INPUT_FILE, PlaneRootSeedDto.class).getPlanes().stream()
                .filter(planeSeedDto -> {
                    boolean isValid = validationUtil.isValid(planeSeedDto);

                    sb.append( isValid ? String.format("Successfully imported Plane %s", planeSeedDto.getRegisterNumber())
                             : "Invalid Plane").append(System.lineSeparator());
                    return isValid;
                }).map(planeSeedDto -> modelMapper.map(planeSeedDto, Plane.class))
                .forEach(this.planeRepository::save);
        return sb.toString();
    }

    @Override
    public Plane findByRegNumber(String number) {
        return this.planeRepository.findPlaneByRegisterNumber(number);
    }
}
