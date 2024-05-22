package com.centralplatform.server.model.Order;

import com.centralplatform.server.model.Location.Location;
import com.centralplatform.server.model.OrderItem.OrderItem;
import com.centralplatform.server.model.Organization.Organization;
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
@Table(name = "_order")
public class Order {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "source_location_id", referencedColumnName = "id")
    private Location sourceLocation;

    @ManyToOne
    @JoinColumn(name = "destination_location_id", referencedColumnName = "id")
    private Location destinationLocation;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
