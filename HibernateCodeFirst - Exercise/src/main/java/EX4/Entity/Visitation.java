package EX4.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.text.DateFormat;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    private String comment;
    private DateFormat visitDate;
    private Patient Patient;

    public Visitation() {
    }
    @Column(name = "comments", columnDefinition = "TEXT")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    @Column(name = "visitation_date")
    public DateFormat getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(DateFormat visitDate) {
        this.visitDate = visitDate;
    }
    @ManyToOne
    public Patient getPatient() {
        return Patient;
    }

    public void setPatient(EX4.Entity.Patient patient) {
        Patient = patient;
    }
}
