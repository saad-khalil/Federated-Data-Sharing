package com.centralplatform.server.model.OrderItem;

import com.centralplatform.server.model.ItemType.ItemType;
import com.centralplatform.server.model.Order.Order;
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
@Table(name = "order_item")
public class OrderItem {

    @Id
    @UuidGenerator
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ItemType itemType;
    private int requestedQuantity;
    private int actualQuantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
