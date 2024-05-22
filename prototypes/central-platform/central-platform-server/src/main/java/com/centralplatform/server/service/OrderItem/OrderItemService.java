package com.centralplatform.server.service.OrderItem;

import com.centralplatform.server.dto.OrderItem.OrderItemDTO;
import com.centralplatform.server.payload.request.OrderItem.OrderItemRequest;

import java.util.List;

public interface OrderItemService<R extends OrderItemRequest> {
    List<OrderItemDTO> getOrderItems(R request);

    OrderItemDTO getOrderItemById(String id);

    OrderItemDTO uploadOrderItem(R request);

    OrderItemDTO updateOrderItem(R request, String id);

    List<OrderItemDTO> uploadOrderItems(R request);

    void deleteOrderItemById(String id);

    void deleteOrderItems(R request);
}
