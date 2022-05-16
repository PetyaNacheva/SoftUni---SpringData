package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.Dto.Xml.PictureRootDto;
import softuni.exam.domain.Dto.Xml.PictureSeedDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    private static final String PICTURE_FILE_PATH ="src/main/resources/files/xml/pictures.xml";
    private final PictureRepository pictureRepository;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public PictureServiceImpl(PictureRepository pictureRepository, ValidatorUtil validatorUtil, XmlParser xmlParser, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public String importPictures() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();

               xmlParser.parseXml(PICTURE_FILE_PATH, PictureRootDto.class).getPicture()
                       .stream().filter(pictureSeedDto -> {
                           boolean isValid = validatorUtil.isValid(pictureSeedDto);

                           sb.append( isValid ? String.format("Successfully imported picture - %s", pictureSeedDto.getUrl())
                                   : "Invalid picture").append(System.lineSeparator());
                           return isValid;
                       }).map(pictureSeedDto -> modelMapper.map(pictureSeedDto, Picture.class))
                       .forEach(this.pictureRepository::save);
        return sb.toString();
    }

    @Override
    public boolean areImported() {

        return this.pictureRepository.count()>0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {

        return Files.readString(Path.of(PICTURE_FILE_PATH));
    }

    @Override
    public Picture findPictureByUrl(String picture) {
        return this.pictureRepository.findPictureByUrl(picture);
    }
}
