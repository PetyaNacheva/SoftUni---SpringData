package exam.model.XMLDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "shops")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedRootDto {
    @XmlElement(name = "shop")
    List<ShopSeedDto> shop;

    public ShopSeedRootDto() {
    }

    public List<ShopSeedDto> getShop() {
        return shop;
    }

    public void setShop(List<ShopSeedDto> shop) {
        this.shop = shop;
    }
}
