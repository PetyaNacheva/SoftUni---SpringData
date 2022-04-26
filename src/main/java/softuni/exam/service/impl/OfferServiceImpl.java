package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Xml.OfferRootDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.AgentService;
import softuni.exam.service.ApartmentService;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private static final String OFFER_FILE_PATH = "C:\\Users\\Pepi\\Desktop\\Java DB Advanced - Exam 2.4.22\\skeleton\\src\\main\\resources\\files\\xml\\offers.xml";

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final AgentService agentService;
    private final ApartmentService apartmentService;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, AgentService agentService, ApartmentService apartmentService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.agentService = agentService;
        this.apartmentService = apartmentService;
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

       // OfferRootDto offerRootDto = xmlParser.parseXml(OFFER_FILE_PATH, OfferRootDto.class);
            xmlParser.parseXml(OFFER_FILE_PATH, OfferRootDto.class).getOffers()
                    .stream().filter(offerDto -> {
                        Agent agent = agentService.findAgentByFirstName(offerDto.getAgent().getName());
                        boolean isValid = validationUtil.isValid(offerDto)&&agent!=null;

                        sb.append(isValid ? String.format("Successfully imported offer %.2f", offerDto.getPrice())
                                : "Invalid offer").append(System.lineSeparator());
                        return isValid;
                    }).map(offerDto -> {
                        Offer offer = modelMapper.map(offerDto, Offer.class);
                        Agent agent = agentService.findAgentByFirstName(offerDto.getAgent().getName());
                        offer.setAgent(agent);
                        Apartment apartment = apartmentService.findApartmentById(offerDto.getApartment().getId());
                        offer.setApartment(apartment);
                        return offer;
                    }).forEach(this.offerRepository::save);
        return sb.toString();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();
        List<Offer> offers = this.offerRepository.findAllByApartmentLikeOrderByAreaInDescThenByPrice();
        for (Offer offer : offers) {
            sb.append(String.format("Agent %s %s with offer №%d:\n" +
                    "   -Apartment area: %.2f\n" +
                    "   --Town: %s\n" +
                    "   ---Price: %.2f$\n", offer.getAgent().getFirstName(), offer.getAgent().getLastName()
                        ,offer.getId(), offer.getApartment().getArea(),offer.getApartment().getTown().getTownName()
            ,offer.getPrice()));
        }
        return sb.toString();
    }
}
