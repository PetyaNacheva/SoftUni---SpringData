package softuni.exam.domain.Dto.Json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class PictureSeedDtoJson {
    @Expose
    private String url;

    public PictureSeedDtoJson() {
    }
    @NotNull
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
