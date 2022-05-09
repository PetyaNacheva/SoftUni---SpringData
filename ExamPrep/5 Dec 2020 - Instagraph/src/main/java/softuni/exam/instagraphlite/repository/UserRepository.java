package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);

    @Query("select distinct u from User as u join fetch u.posts p order by size(p) desc, u.id ")
    List<User>findAllUsersByPostCountDescThenByUserId();
}
