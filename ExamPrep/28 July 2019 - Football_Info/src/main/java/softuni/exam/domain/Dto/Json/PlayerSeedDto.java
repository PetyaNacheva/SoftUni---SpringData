package softuni.exam.domain.Dto.Json;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PlayerSeedDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int number;
    @Expose
    private BigDecimal salary;
    @Expose
    private String position;
    @Expose
    private PictureSeedDtoJson picture;
    @Expose
    private TeamSeedDtoJson team;

    public PlayerSeedDto() {
    }
    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Length(min = 3, max = 15)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Min(1)
    @Max(99)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    @Positive
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    @Length(max = 2, min = 2)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PictureSeedDtoJson getPicture() {
        return picture;
    }

    public void setPicture(PictureSeedDtoJson picture) {
        this.picture = picture;
    }

    public TeamSeedDtoJson getTeam() {
        return team;
    }

    public void setTeam(TeamSeedDtoJson team) {
        this.team = team;
    }
}
