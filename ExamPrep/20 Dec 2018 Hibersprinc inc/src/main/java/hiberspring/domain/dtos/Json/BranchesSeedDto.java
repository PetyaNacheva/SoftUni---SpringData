package hiberspring.domain.dtos.Json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class BranchesSeedDto {
    @Expose
    private String name;
    @Expose
    private String town;

    public BranchesSeedDto() {
    }
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotNull
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
