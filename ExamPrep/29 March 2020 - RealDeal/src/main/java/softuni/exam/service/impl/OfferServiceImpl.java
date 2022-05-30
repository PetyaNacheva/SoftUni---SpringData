package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Dto.XmlDtos.OfferRootDto;
import softuni.exam.models.Dto.XmlDtos.OfferSeedDto;
import softuni.exam.models.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {
    private static final String OFFER_FILE_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count()>0;
    }

    @Override
    public String readOffersFileContent() throws IOException {

        return Files.readString(Path.of(OFFER_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser.parseXml(OFFER_FILE_PATH, OfferRootDto.class).getOffers()
                .stream().filter(offerSeedDto -> {
                    boolean isValid = validationUtil.isValid(offerSeedDto);
                    sb.append(isValid ?  String.format("Successfully offer - %s - %s",offerSeedDto.getAddedOn(), offerSeedDto.isHasGoldStatus())
                            : "Invalid offer").append(System.lineSeparator());
                    return isValid;
                })
                .map(offerSeedDto -> modelMapper.map(offerSeedDto, Offer.class))
                .forEach(offerRepository::save);
        return sb.toString();
    }
}
