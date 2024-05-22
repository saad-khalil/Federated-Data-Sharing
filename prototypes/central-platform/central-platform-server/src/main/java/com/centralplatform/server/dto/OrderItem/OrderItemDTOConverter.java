package com.centralplatform.server.dto.OrderItem;

import com.centralplatform.server.dto.Inventory.InventoryDTO;
import com.centralplatform.server.model.OrderItem.OrderItem;
import com.centralplatform.server.util.Converters.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class OrderItemDTOConverter implements GenericConverter<OrderItem, OrderItemDTO> {
    @Override
    public OrderItemDTO apply(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setItemType(orderItem.getItemType());
        dto.setActualQuantity(orderItem.getActualQuantity());
        dto.setRequestedQuantity(orderItem.getRequestedQuantity());
        return dto;
    }

    @Override
    public <V> Function<V, OrderItemDTO> compose(Function<? super V, ? extends OrderItem> before) {
        return GenericConverter.super.compose(before);
    }

    @Override
    public <V> Function<OrderItem, V> andThen(Function<? super OrderItemDTO, ? extends V> after) {
        return GenericConverter.super.andThen(after);
    }

    @Override
    public OrderItemDTO convert(OrderItem input) {
        return GenericConverter.super.convert(input);
    }

    @Override
    public List<OrderItemDTO> convert(List<OrderItem> input) {
        return GenericConverter.super.convert(input);
    }
}
