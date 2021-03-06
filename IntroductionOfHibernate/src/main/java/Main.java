import entities.Town;

import org.hibernate.boot.archive.scan.spi.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("PU_Name");
        EntityManager entityManager = emf.createEntityManager();

        Engine engine = new Engine(entityManager);
        engine.run();
    }
}
