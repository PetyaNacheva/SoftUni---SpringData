package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.Entity.Passenger;

import java.util.List;
import java.util.Set;

@Repository
public interface PassengerRepository  extends JpaRepository<Passenger, Long> {
    Passenger findPassengerByEmail(String email);

  /*  @Query("select p.firstName, p.lastName, count(t.id) as ticket_count from Passenger as p\n" +
            "join Ticket t on p.id = t.passenger.id\n" +
            "order by ticket_count desc , p.email")
    List<Object[]>finAllByTicketsCountInDescOrderThenByEmail();*/
  @Query("SELECT p FROM  Passenger p " +
          "ORDER BY p.tickets.size DESC, p.email")
  Set<Passenger> findAllOrderByTicketCountAndEmail();
}
