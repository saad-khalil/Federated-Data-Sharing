package com.centralplatform.server.service.Location;

import com.centralplatform.server.dto.Location.LocationDTO;
import com.centralplatform.server.dto.Location.LocationDTOConverter;
import com.centralplatform.server.model.Location.Location;
import com.centralplatform.server.payload.request.Location.LocationRequest;
import com.centralplatform.server.repository.Location.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationDTOConverter converter;

    @Override
    public List<LocationDTO> getLocations(LocationRequest request) {
        return converter.convert(locationRepository.findAll());
    }

    @Override
    public LocationDTO getLocationById(String id) {
        return converter.convert(locationRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public LocationDTO uploadLocation(LocationRequest request) {
        Location location = Location.builder().build();
        return converter.convert(locationRepository.save(location));
    }

    @Override
    public LocationDTO updateLocation(LocationRequest request, String id) {
        return null;
    }

    @Override
    public List<LocationDTO> uploadLocations(LocationRequest request) {
        List<Location> locationList = new ArrayList<>();
        return converter.convert(locationList);
    }

    @Override
    public void deleteLocationById(String id) {
        locationRepository.delete(locationRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public void deleteLocations(LocationRequest request) {
        List<UUID> locationIdList = new ArrayList<>();
        locationRepository.deleteAll(locationRepository.findAllById(locationIdList));
    }
}
