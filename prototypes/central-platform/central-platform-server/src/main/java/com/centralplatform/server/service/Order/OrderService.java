package com.centralplatform.server.service.Order;

import com.centralplatform.server.dto.Order.OrderDTO;
import com.centralplatform.server.payload.request.Order.OrderRequest;

import java.util.List;

public interface OrderService<R extends OrderRequest> {
    List<OrderDTO> getOrders(R request);

    OrderDTO getOrderById(String id);

    OrderDTO uploadOrder(R request);

    OrderDTO updateOrder(R request, String id);

    List<OrderDTO> uploadOrders(R request);

    void deleteOrderById(String id);

    void deleteOrders(R request);
}
