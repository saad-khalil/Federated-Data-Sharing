package com.centralplatform.server.dto.Order;

import com.centralplatform.server.model.Order.Order;
import com.centralplatform.server.util.Converters.GenericConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class OrderDTOConverter implements GenericConverter<Order, OrderDTO> {
    @Override
    public OrderDTO apply(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderStatus(order.getStatus());
        dto.setOrderItems(order.getOrderItems());
        dto.setDestinationLocation(order.getDestinationLocation());
        dto.setSourceLocation(order.getSourceLocation());
        return dto;
    }

    @Override
    public <V> Function<V, OrderDTO> compose(Function<? super V, ? extends Order> before) {
        return GenericConverter.super.compose(before);
    }

    @Override
    public <V> Function<Order, V> andThen(Function<? super OrderDTO, ? extends V> after) {
        return GenericConverter.super.andThen(after);
    }

    @Override
    public OrderDTO convert(Order input) {
        return GenericConverter.super.convert(input);
    }

    @Override
    public List<OrderDTO> convert(List<Order> input) {
        return GenericConverter.super.convert(input);
    }
}
