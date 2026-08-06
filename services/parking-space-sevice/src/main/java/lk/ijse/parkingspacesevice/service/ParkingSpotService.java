package lk.ijse.parkingspacesevice.service;



import lk.ijse.parkingspacesevice.dto.OwnerDashboardDTO;
import lk.ijse.parkingspacesevice.dto.OwnerRevenueReportDTO;
import lk.ijse.parkingspacesevice.dto.ParkingSpotRequestDTO;
import lk.ijse.parkingspacesevice.dto.ParkingSpotResponseDTO;

import java.util.List;

public interface ParkingSpotService {
    ParkingSpotResponseDTO addParkingSpot(ParkingSpotRequestDTO requestDTO);
    ParkingSpotResponseDTO updateParkingSpot(String spotId, ParkingSpotRequestDTO requestDTO);
    void deleteParkingSpot(String spotId);
    ParkingSpotResponseDTO getParkingSpotById(String spotId);
    List<ParkingSpotResponseDTO> getSpotsByOwnerId(String ownerId);
    OwnerDashboardDTO getOwnerDashboard(String ownerId);
    OwnerRevenueReportDTO getOwnerRevenueReport(String ownerId);


}