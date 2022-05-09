package softuni.exam.instagraphlite.models.Dto.Json.Xml;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)

public class PostSeedDto {
    @XmlElement
    private String caption;
    @XmlElement(name = "user")
    private UserXMLSeedDto user;
    @XmlElement(name = "picture")
    private PictureXMLSeedDto picture;

    public PostSeedDto() {
    }
    @Size(min = 21)
    @NotNull
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    @NotNull
    public UserXMLSeedDto getUser() {
        return user;
    }

    public void setUser(UserXMLSeedDto user) {
        this.user = user;
    }
    @NotNull
    public PictureXMLSeedDto getPicture() {
        return picture;
    }

    public void setPicture(PictureXMLSeedDto picture) {
        this.picture = picture;
    }
}
