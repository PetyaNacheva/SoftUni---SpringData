package hiberspring.service.Impl;

import hiberspring.domain.dtos.Xml.ProductRootSeedDto;
import hiberspring.domain.dtos.Xml.ProductsSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.ProductRepository;
import hiberspring.service.BranchService;
import hiberspring.service.ProductService;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_FILE_PATH="src/main/resources/files/products.xml";
    
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final BranchService branchService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, BranchService branchService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.branchService = branchService;
    }

    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count()>0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return Files.readString(Path.of(PRODUCT_FILE_PATH));
    }

    @Override
    public String importProducts() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
       // ProductRootSeedDto productRootSeedDto = xmlParser.parseXml(PRODUCT_FILE_PATH, ProductRootSeedDto.class);
        xmlParser.parseXml(PRODUCT_FILE_PATH, ProductRootSeedDto.class).getProducts()
                .stream().filter(productsSeedDto -> {
                    Branch branch = this.branchService.findBranchByName(productsSeedDto.getBranch());
                    boolean isValid = validationUtil.isValid(productsSeedDto)&&branch!=null;
                    sb.append(isValid ? String.format("Successfully imported Product %s.", productsSeedDto.getName())
                            : "Error: Invalid data.").append(System.lineSeparator());

                    return isValid;
                }).map(productsSeedDto -> {
                   Product product =  modelMapper.map(productsSeedDto, Product.class);
                    product.setClients(Integer.parseInt(productsSeedDto.getClients()));

                    Branch branch = this.branchService.findBranchByName(productsSeedDto.getBranch());
                    product.setBranch(branch);
                    return product;
                })
                .forEach(this.productRepository::saveAndFlush);
        return sb.toString();
    }
}
