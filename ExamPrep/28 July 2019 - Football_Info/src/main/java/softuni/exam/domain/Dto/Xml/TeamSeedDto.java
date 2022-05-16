package softuni.exam.domain.Dto.Xml;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedDto {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "picture")
    private PictureSeedDto picture;

    public TeamSeedDto() {
    }
    @Length(min = 3, max = 21)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotNull
    public PictureSeedDto getPicture() {
        return picture;
    }

    public void setPicture(PictureSeedDto picture) {
        this.picture = picture;
    }
}
