package softuni.exam.domain.Dto.Xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "pictures")
public class PictureRootDto {
    @XmlElement(name = "picture")
    private List<PictureSeedDto> picture;

    public PictureRootDto() {
    }

    public List<PictureSeedDto> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureSeedDto> picture) {
        this.picture = picture;
    }
}
