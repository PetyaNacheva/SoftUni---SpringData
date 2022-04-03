package EX4.Entity;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity{

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private DateFormat dateOfBirth;
    private String picture;
    private boolean hasMedicalInsurance;

    private Set<Diagnose> diagnoses;
    private Set<Visitation> visitations;



    public Patient() {
        this.diagnoses = new HashSet<>();
        this.visitations = new HashSet<>();
    }
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "date_of_bith")
    public DateFormat getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(DateFormat dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    @Column(name = "picture", columnDefinition = "BLOB")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    @Column(name = "has_medical_insurance")
    public boolean isHasMedicalInsurance() {
        return hasMedicalInsurance;
    }

    public void setHasMedicalInsurance(boolean hasMedicalInsurance) {
        this.hasMedicalInsurance = hasMedicalInsurance;
    }
    @OneToMany(mappedBy = "patients", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }
}
