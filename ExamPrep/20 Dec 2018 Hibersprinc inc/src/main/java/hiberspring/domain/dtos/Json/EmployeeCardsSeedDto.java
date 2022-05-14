package hiberspring.domain.dtos.Json;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class EmployeeCardsSeedDto {
    @Expose
    private String number;

    public EmployeeCardsSeedDto() {
    }
    @NotNull
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
