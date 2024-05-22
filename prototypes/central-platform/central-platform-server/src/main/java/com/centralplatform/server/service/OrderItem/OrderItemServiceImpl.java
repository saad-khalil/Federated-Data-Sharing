package com.centralplatform.server.service.OrderItem;

import com.centralplatform.server.dto.OrderItem.OrderItemDTO;
import com.centralplatform.server.dto.OrderItem.OrderItemDTOConverter;
import com.centralplatform.server.model.OrderItem.OrderItem;
import com.centralplatform.server.payload.request.OrderItem.OrderItemRequest;
import com.centralplatform.server.repository.OrderItem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemDTOConverter converter;

    @Override
    public List<OrderItemDTO> getOrderItems(OrderItemRequest request) {
        return converter.convert(orderItemRepository.findAll());
    }

    @Override
    public OrderItemDTO getOrderItemById(String id) {
        return converter.convert(orderItemRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public OrderItemDTO uploadOrderItem(OrderItemRequest request) {
        OrderItem orderItem = OrderItem.builder().build();
        return converter.convert(orderItemRepository.save(orderItem));
    }

    @Override
    public OrderItemDTO updateOrderItem(OrderItemRequest request, String id) {
        return null;
    }

    @Override
    public List<OrderItemDTO> uploadOrderItems(OrderItemRequest request) {
        List<OrderItem> orderItemList = new ArrayList<>();
        return converter.convert(orderItemList);
    }

    @Override
    public void deleteOrderItemById(String id) {
        orderItemRepository.delete(orderItemRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public void deleteOrderItems(OrderItemRequest request) {
        List<UUID> orderItemIdList = new ArrayList<>();
        orderItemRepository.deleteAll(orderItemRepository.findAllById(orderItemIdList));
    }
}
