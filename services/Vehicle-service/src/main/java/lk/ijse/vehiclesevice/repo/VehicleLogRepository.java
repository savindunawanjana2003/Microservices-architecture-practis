package lk.ijse.vehiclesevice.repo;

import lk.ijse.vehiclesevice.entity.VehicleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import lk.ijse.vehiclesevice.dto.Enum.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleLogRepository extends JpaRepository<VehicleLog, String> {
    List<VehicleLog> findByVehicleId(String vehicleId);
    Optional<VehicleLog> findByVehicleIdAndStatus(String vehicleId, Status status);
}