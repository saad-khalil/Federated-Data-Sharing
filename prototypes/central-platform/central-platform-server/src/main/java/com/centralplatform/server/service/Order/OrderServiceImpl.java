package com.centralplatform.server.service.Order;

import com.centralplatform.server.dto.Order.OrderDTO;
import com.centralplatform.server.dto.Order.OrderDTOConverter;
import com.centralplatform.server.model.Order.Order;
import com.centralplatform.server.payload.request.Order.OrderRequest;
import com.centralplatform.server.repository.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDTOConverter converter;

    @Override
    public List<OrderDTO> getOrders(OrderRequest request) {
        return converter.convert(orderRepository.findAll());
    }

    @Override
    public OrderDTO getOrderById(String id) {
        return converter.convert(orderRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public OrderDTO uploadOrder(OrderRequest request) {
        Order order = Order.builder().build();
        return converter.convert(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrder(OrderRequest request, String id) {
        return null;
    }

    @Override
    public List<OrderDTO> uploadOrders(OrderRequest request) {
        List<Order> orderList = new ArrayList<>();
        return converter.convert(orderList);
    }

    @Override
    public void deleteOrderById(String id) {
    orderRepository.delete(orderRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public void deleteOrders(OrderRequest request) {
        List<UUID> orderIdList = new ArrayList<>();
        orderRepository.deleteAll(orderRepository.findAllById(orderIdList));
    }
}
