package EX4.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseEntity{
    private String name;
    private String diagnoseComment;
    private Patient patients;
    private Set<Medicament> prescribedMedicaments;

    public Diagnose() {
    this.prescribedMedicaments = new HashSet<>();
    }
    @Column(name = "diagnose_name", columnDefinition = "TEXT")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "diagnose_comment",columnDefinition = "TEXT")
    public String getDiagnoseComment() {
        return diagnoseComment;
    }

    public void setDiagnoseComment(String diagnoseComment) {
        this.diagnoseComment = diagnoseComment;
    }
    @ManyToOne
    public Patient getPatients() {
        return patients;
    }

    public void setPatients(Patient patients) {
        this.patients = patients;
    }
    @ManyToMany
   /* @JoinTable(name = "diagnoses_prescripted_medicament", joinColumns = @JoinColumn(name = "diagnoses_id",  referencedColumnName= "id"),
    inverseJoinColumns = @JoinColumn (name = "medicament_id", referencedColumnName = "id"))*/
    public Set<Medicament> getPrescribedMedicaments() {
        return prescribedMedicaments;
    }

    public void setPrescribedMedicaments(Set<Medicament> medicaments) {
        this.prescribedMedicaments = medicaments;
    }
}
