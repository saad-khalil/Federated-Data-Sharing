package com.centralplatform.server.model.Location;

import com.centralplatform.server.model.Inventory.Inventory;
import com.centralplatform.server.model.Organization.Organization;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.centralplatform.server.model.Order.Order;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
public class Location {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value = "organization-location")
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
    @Column(name = "inventory")
    @JsonManagedReference(value = "location-inventory")
    @OneToMany(mappedBy = "location")
    private List<Inventory> inventory;

    @OneToMany(mappedBy = "sourceLocation")
    private List<Order> sourceOrders;

    @OneToMany(mappedBy = "destinationLocation")
    private List<Order> destinationOrders;
}
