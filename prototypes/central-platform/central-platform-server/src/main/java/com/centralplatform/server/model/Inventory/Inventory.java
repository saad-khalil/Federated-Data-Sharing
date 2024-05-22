package com.centralplatform.server.model.Inventory;

import com.centralplatform.server.model.ItemType.ItemType;
import com.centralplatform.server.model.Location.Location;
import com.centralplatform.server.model.Organization.Organization;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @UuidGenerator
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ItemType itemType;
    private int quantity;

    private int capacity;

    @ManyToOne
    @JsonBackReference(value = "location-inventory")
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne
    @JsonBackReference(value = "organization-inventory")
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;
}
