package lk.ijse.parkingspacesevice.service.impl;


import lk.ijse.parkingspacesevice.dto.*;
import lk.ijse.parkingspacesevice.dto.Enum.SpotStatus;
import lk.ijse.parkingspacesevice.entity.ParkingSpot;
import lk.ijse.parkingspacesevice.feign.BookingServiceClient;
import lk.ijse.parkingspacesevice.repo.ParkingSpotRepository;
import lk.ijse.parkingspacesevice.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository repository;
    private final ModelMapper modelMapper;
    // Required Dependencies Inject කරගන්න
    private final BookingServiceClient bookingServiceClient;

    @Override
    public ParkingSpotResponseDTO addParkingSpot(ParkingSpotRequestDTO dto) {
        ParkingSpot spot = ParkingSpot.builder()
                .ownerId(dto.getOwnerId())
                .title(dto.getTitle())
                .location(dto.getLocation())
                .totalSpots(dto.getTotalSpots())
                .availableSpots(dto.getTotalSpots())
                .pricePerHour(dto.getPricePerHour())
                .status(SpotStatus.ACTIVE)
                .build();

        ParkingSpot saved = repository.save(spot);
        return modelMapper.map(saved, ParkingSpotResponseDTO.class);
    }

    @Override
    public ParkingSpotResponseDTO updateParkingSpot(String spotId, ParkingSpotRequestDTO dto) {
        ParkingSpot spot = repository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Parking Space not found for ID: " + spotId));

        spot.setTitle(dto.getTitle());
        spot.setLocation(dto.getLocation());
        spot.setTotalSpots(dto.getTotalSpots());
        spot.setPricePerHour(dto.getPricePerHour());

        ParkingSpot updated = repository.save(spot);
        return modelMapper.map(updated, ParkingSpotResponseDTO.class);
    }

    @Override
    public void deleteParkingSpot(String spotId) {
        ParkingSpot spot = repository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Parking Space not found for ID: " + spotId));
        repository.delete(spot);
    }

    @Override
    public ParkingSpotResponseDTO getParkingSpotById(String spotId) {
        ParkingSpot spot = repository.findById(spotId)
                .orElseThrow(() -> new RuntimeException("Parking Space not found for ID: " + spotId));
        return modelMapper.map(spot, ParkingSpotResponseDTO.class);
    }

    @Override
    public List<ParkingSpotResponseDTO> getSpotsByOwnerId(String ownerId) {
        return repository.findByOwnerId(ownerId).stream()
                .map(spot -> modelMapper.map(spot, ParkingSpotResponseDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public OwnerDashboardDTO getOwnerDashboard(String ownerId) {
        List<ParkingSpot> ownerSpots = repository.findByOwnerId(ownerId);

        int totalCapacity = ownerSpots.stream().mapToInt(ParkingSpot::getTotalSpots).sum();
        int totalAvailable = ownerSpots.stream().mapToInt(ParkingSpot::getAvailableSpots).sum();
        int totalOccupied = totalCapacity - totalAvailable;

        List<ParkingSpotResponseDTO> zoneDTOs = ownerSpots.stream()
                .map(spot -> modelMapper.map(spot, ParkingSpotResponseDTO.class))
                .collect(Collectors.toList());

        return OwnerDashboardDTO.builder()
                .ownerId(ownerId)
                .totalZones(ownerSpots.size())
                .totalCapacity(totalCapacity)
                .totalAvailableSpots(totalAvailable)
                .totalOccupiedSpots(totalOccupied)
                .zones(zoneDTOs)
                .build();
    }

    //---------------------------------------------------------------------------------
    @Override
    public OwnerRevenueReportDTO getOwnerRevenueReport(String ownerId) {
        List<ParkingSpot> ownerSpots = repository.findByOwnerId(ownerId);

        List<BookingDTO> allBookings = new ArrayList<>();
        BigDecimal totalEarnings = BigDecimal.ZERO;

        for (ParkingSpot spot : ownerSpots) {
            // Booking Service eken spotId ekata adala Bookings laba ganima
            List<BookingDTO> spotBookings = bookingServiceClient.getBookingsBySpotId(spot.getSpotId());

            for (BookingDTO booking : spotBookings) {
                if ("COMPLETED".equalsIgnoreCase(booking.getStatus()) || "CONFIRMED".equalsIgnoreCase(booking.getStatus())) {
                    if (booking.getAmount() != null) {
                        totalEarnings = totalEarnings.add(booking.getAmount());
                    }
                }
                allBookings.add(booking);
            }
        }

        return OwnerRevenueReportDTO.builder()
                .ownerId(ownerId)
                .totalEarnings(totalEarnings)
                .totalBookingsCount(allBookings.size())
                .bookingHistory(allBookings)
                .build();
    }
}