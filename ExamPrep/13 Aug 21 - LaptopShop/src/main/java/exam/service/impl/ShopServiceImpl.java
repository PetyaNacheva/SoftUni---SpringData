package exam.service.impl;

import exam.model.Shop;
import exam.model.Town;
import exam.model.XMLDto.ShopSeedRootDto;
import exam.repository.ShopRepository;
import exam.service.ShopService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XMLParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ShopServiceImpl implements ShopService {
    private static final String SHOP_INPUT_FILE= "src/main/resources/files/xml/shops.xml";
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XMLParser xmlParser;
    private final TownService townService;

    public ShopServiceImpl(ShopRepository shopRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XMLParser xmlParser, TownService townService) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count()>0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOP_INPUT_FILE));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
      //  List<ShopSeedDto> shops = this.xmlParser.parseXml(SHOP_INPUT_FILE, ShopSeedRootDto.class).getShop();
        this.xmlParser.parseXml(SHOP_INPUT_FILE, ShopSeedRootDto.class).getShop().stream()
                .filter(shopSeedDto -> {
                    Shop shop =this.shopRepository.findByName(shopSeedDto.getName());
                    boolean isValid=validationUtil.isValid(shopSeedDto)&&shop==null;

                    sb.append(isValid ? String.format("Successfully imported Shop %s - %.0f", shopSeedDto.getName(), shopSeedDto.getIncome())
                                    : "Invalid shop")
                            .append(System.lineSeparator());
                    return isValid;
                }).map(shopSeedDto -> {
                    Shop shop = modelMapper.map(shopSeedDto, Shop.class);
                    Town town = townService.getTownByName(shopSeedDto.getTown().getName());
                    shop.setTown(town);
                   return shop;


                })
                .forEach(this.shopRepository::save);


        return sb.toString();
    }
    @Override
    public Shop getShopByName(String name){
        return this.shopRepository.findByName(name);
    }
}
