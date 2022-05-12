package exam.service.impl;

import com.google.gson.Gson;
import exam.model.JsonDto.LaptopSalesByBestLaptopDto;
import exam.model.JsonDto.LaptopSeedDto;
import exam.model.Laptop;
import exam.model.Shop;
import exam.model.WarrantyType;
import exam.repository.LaptopRepository;
import exam.service.LaptopService;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {
    private static final String LAPTOP_INPUT_PATH="src/main/resources/files/json/laptops.json";
    private final LaptopRepository laptopRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final ShopService shopService;

    public LaptopServiceImpl(LaptopRepository laptopRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, ShopService shopService) {
        this.laptopRepository = laptopRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.shopService = shopService;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count()>0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOP_INPUT_PATH));
    }

    @Override
    public String importLaptops() throws IOException {

        StringBuilder sb = new StringBuilder();
        List<String> warranty= Arrays.stream(WarrantyType.values()).map(w->w.toString()).collect(Collectors.toList());
        //LaptopSeedDto [] laptopSeedDtos = this.gson.fromJson(readLaptopsFileContent(), LaptopSeedDto[].class);
        Arrays.stream(this.gson.fromJson(readLaptopsFileContent(), LaptopSeedDto[].class))
                .filter(laptopSeedDto -> {
                    Laptop laptop = this.laptopRepository.findByMacAddress(laptopSeedDto.getMacAddress());
                    Shop shop = this.shopService.getShopByName(laptopSeedDto.getShop().getName());
                    boolean isValid = validationUtil.isValid(laptopSeedDto)&&laptop==null;
                    if(!warranty.contains(laptopSeedDto.getWarrantyType())||laptopSeedDto.getWarrantyType().isEmpty()||shop==null){
                            isValid = false;
                    }
                sb.append( isValid ? String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                                                        laptopSeedDto.getMacAddress(),
                                                        laptopSeedDto.getCpuSpeed(),
                                                        laptopSeedDto.getRam(), laptopSeedDto.getStorage())
                        : "Invalid laptop").append(System.lineSeparator());
                return isValid;
                }).map(laptopSeedDto -> {
                    Laptop laptop = modelMapper.map(laptopSeedDto, Laptop.class);
                    Shop shop = this.shopService.getShopByName(laptopSeedDto.getShop().getName());
                    WarrantyType warrantyType = WarrantyType.valueOf(laptopSeedDto.getWarrantyType());
                    laptop.setWarrantyType(warrantyType);
                    laptop.setShop(shop);
                    return laptop;
                }).forEach(this.laptopRepository::save);
        return sb.toString();
    }

    @Override
    public String exportBestLaptops() {
        StringBuilder sb = new StringBuilder();
        List<Object[]> laptops = this.laptopRepository.findAllByCpuSpeedAndRamAndStorageDescAndMac();
        List<LaptopSalesByBestLaptopDto> bestLaptopDtos = new ArrayList<>();
        for (Object[] laptop : laptops) {
            LaptopSalesByBestLaptopDto bestLaptop = new LaptopSalesByBestLaptopDto();
            bestLaptop.setMacAddress((String)laptop[0]);
            bestLaptop.setCpuSpeed((Double) laptop[1]);
            bestLaptop.setRam((int) laptop[2]);
            bestLaptop.setStorage((int) laptop[3]);
            bestLaptop.setPrice((BigDecimal) laptop[4]);
            bestLaptop.setShopName((String) laptop[5]);
            bestLaptop.setTownName((String) laptop[6]);
            bestLaptopDtos.add(bestLaptop);

        }
        bestLaptopDtos.stream().forEach(laptop->{
            sb.append(String.format("Laptop - %s))",laptop.getMacAddress())).append(System.lineSeparator());
            sb.append(String.format("*Cpu speed - %.2f", laptop.getCpuSpeed())).append(System.lineSeparator());
            sb.append(String.format("**Ram - %d", laptop.getRam())).append(System.lineSeparator());
            sb.append(String.format("***Storage - %d", laptop.getStorage())).append(System.lineSeparator());
            sb.append(String.format("****Price - %.2f", laptop.getPrice())).append(System.lineSeparator());
            sb.append(String.format("#Shop name - %s", laptop.getShopName())).append(System.lineSeparator());
            sb.append(String.format("##Town - %s", laptop.getTownName())).append(System.lineSeparator());
            sb.append(System.lineSeparator());
        });
        sb.append(System.lineSeparator());
    return sb.toString();
    }
}
