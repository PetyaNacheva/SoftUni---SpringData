package exam.repository;

import exam.model.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    Laptop findByMacAddress(String macAddress);

    @Query("select l.macAddress, l.cpuSpeed, l.ram, l.storage, l.price, s.name, t.name from Laptop as l\n" +
            "join  Shop as s on s.id = l.shop.id\n" +
            "join Town as t on t.id = s.town.id\n" +
            "order by l.cpuSpeed desc, l.ram desc, l.storage desc, l.macAddress")
    List<Object[]> findAllByCpuSpeedAndRamAndStorageDescAndMac();
   // List<Object[]>findLaptopsByCpuSpeedOrderByCpuSpeedIdDescThenByRamInDescThenByStorageInDescThenByMac();//
}
