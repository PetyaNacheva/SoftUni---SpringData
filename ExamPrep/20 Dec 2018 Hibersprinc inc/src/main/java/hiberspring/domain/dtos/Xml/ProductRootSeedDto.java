package hiberspring.domain.dtos.Xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "products")
public class ProductRootSeedDto {
    @XmlElement(name = "product")
    private List<ProductsSeedDto> products;

    public ProductRootSeedDto() {
    }

    public List<ProductsSeedDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsSeedDto> products) {
        this.products = products;
    }
}
