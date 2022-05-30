package softuni.exam.models.Dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

public class CarSeedDto {

    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Integer kilometers;
    @Expose
    private String registeredOn;

    public CarSeedDto() {
    }

    @Size(min = 2, max = 19, message = "invalid car make")
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
    @Size(min = 2, max = 19, message = "invalid car model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @Positive(message = "kilometers must be positive")
    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registerOn) {
        this.registeredOn = registerOn;
    }
}
