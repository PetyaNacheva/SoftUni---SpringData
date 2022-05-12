package exam.model.JsonDto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ShopNameDto {
    @Expose
    private String name;

    public ShopNameDto() {
    }
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
