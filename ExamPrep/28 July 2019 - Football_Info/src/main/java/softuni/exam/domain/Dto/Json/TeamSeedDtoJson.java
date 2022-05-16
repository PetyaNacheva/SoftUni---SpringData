package softuni.exam.domain.Dto.Json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class TeamSeedDtoJson {
    @Expose
    private String name;
    @Expose
    private PictureSeedDtoJson picture;

    public TeamSeedDtoJson() {
    }
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotNull
    public PictureSeedDtoJson getPicture() {
        return picture;
    }

    public void setPicture(PictureSeedDtoJson picture) {
        this.picture = picture;
    }
}
