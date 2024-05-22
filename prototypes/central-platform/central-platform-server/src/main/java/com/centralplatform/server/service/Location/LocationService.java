package com.centralplatform.server.service.Location;

import com.centralplatform.server.dto.Location.LocationDTO;
import com.centralplatform.server.payload.request.Location.LocationRequest;

import java.util.List;

public interface LocationService<R extends LocationRequest>{
    List<LocationDTO> getLocations(R request);

    LocationDTO getLocationById(String id);

    LocationDTO uploadLocation(R request);

    LocationDTO updateLocation(R request, String id);

    List<LocationDTO> uploadLocations(R request);

    void deleteLocationById(String id);

    void deleteLocations(R request);
}
