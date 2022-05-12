package exam.repository;


import exam.model.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    boolean findByName(String name);

    Town findTownByName(String name);
}
