package com.centralplatform.server.payload.request.Location;

import com.centralplatform.server.model.Location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LocationUpdateRequest extends LocationRequest {

    Location location;
}
