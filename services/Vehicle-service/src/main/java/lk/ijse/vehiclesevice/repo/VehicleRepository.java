package lk.ijse.vehiclesevice.repo;

import lk.ijse.vehiclesevice.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findByUserId(String userId);
    boolean existsByLicensePlateNumber(String licensePlateNumber);
}