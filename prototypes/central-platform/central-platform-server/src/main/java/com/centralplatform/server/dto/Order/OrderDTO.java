package com.centralplatform.server.dto.Order;

import com.centralplatform.server.model.Location.Location;
import com.centralplatform.server.model.Order.OrderStatus;
import com.centralplatform.server.model.OrderItem.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO {
    OrderStatus orderStatus;

    Location sourceLocation;

    Location destinationLocation;

    List<OrderItem> orderItems;
}
