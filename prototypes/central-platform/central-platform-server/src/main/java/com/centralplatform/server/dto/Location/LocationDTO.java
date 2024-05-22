package com.centralplatform.server.dto.Location;

import com.centralplatform.server.model.Inventory.Inventory;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LocationDTO {

     String name;
     String address;
     String latitude;
     String longitude;
     List<Inventory> inventory;
}
