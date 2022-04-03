package EX4.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "prescript_medicaments")
public class Medicament extends BaseEntity{

    private String medicamentName;

    public Medicament() {
    }
    @Column (name = "medicament_name", length = 200)
    public String getMedicamentName() {
        return medicamentName;
    }

    public void setMedicamentName(String medicamentName) {
        this.medicamentName = medicamentName;
    }

}
