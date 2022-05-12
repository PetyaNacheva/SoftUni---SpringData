package exam.model.XMLDto;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopSeedDto {
    @XmlElement
    private String address;
    @XmlElement(name = "employee-count")
    private int employeeCount;
    @XmlElement
    private BigDecimal income;
    @XmlElement
    private String name;
    @XmlElement(name = "shop-area")
    private int shopArea;
    @XmlElement(name = "town")
    private TownSeedDto town;

    public ShopSeedDto() {
    }
    @Size(min = 4)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Min(1)
    @Max(49)
    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }
    @Min(20000)
    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
    @Size(min = 4)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Min(150)
    public int getShopArea() {
        return shopArea;
    }

    public void setShopArea(int shopArea) {
        this.shopArea = shopArea;
    }
    @NotNull
    public TownSeedDto getTown() {
        return town;
    }

    public void setTown(TownSeedDto town) {
        this.town = town;
    }
}
