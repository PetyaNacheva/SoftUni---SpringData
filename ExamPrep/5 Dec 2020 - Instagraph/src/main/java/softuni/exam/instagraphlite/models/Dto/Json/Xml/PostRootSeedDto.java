package softuni.exam.instagraphlite.models.Dto.Json.Xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostRootSeedDto {
    @XmlElement(name = "post")
    Set<PostSeedDto> posts;

    public PostRootSeedDto() {
    }

    public Set<PostSeedDto> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostSeedDto> posts) {
        this.posts = posts;
    }
}
