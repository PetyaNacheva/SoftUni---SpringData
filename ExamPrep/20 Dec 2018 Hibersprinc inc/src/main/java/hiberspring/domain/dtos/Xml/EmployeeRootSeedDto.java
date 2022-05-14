package hiberspring.domain.dtos.Xml;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "employees")
public class EmployeeRootSeedDto {
    @XmlElement(name = "employee")
    private List<EmployeeSeedDto> employees;

    public EmployeeRootSeedDto() {
    }

    public List<EmployeeSeedDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeSeedDto> employees) {
        this.employees = employees;
    }
}
