package lk.ijse.parkingspacesevice.service.impl;


import lk.ijse.parkingspacesevice.dto.Enum.SpotStatus;
import lk.ijse.parkingspacesevice.dto.ParkingSpotRequestDTO;
import lk.ijse.parkingspacesevice.dto.ParkingSpotResponseDTO;
import lk.ijse.parkingspacesevice.entity.ParkingSpot;
import lk.ijse.parkingspacesevice.repo.ParkingSpotRepository;
import lk.ijse.parkingspacesevice.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository repository;
    private final ModelMapper modelMapper;

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
}