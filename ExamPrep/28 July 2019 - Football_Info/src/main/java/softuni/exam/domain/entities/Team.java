package softuni.exam.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "teams")
public class Team extends BaseEntities{
    private String name;
    private Picture picture;

    public Team() {
    }
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
