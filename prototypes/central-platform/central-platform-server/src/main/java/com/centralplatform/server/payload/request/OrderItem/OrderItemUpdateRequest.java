package com.centralplatform.server.payload.request.OrderItem;

import com.centralplatform.server.model.OrderItem.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderItemUpdateRequest extends OrderItemRequest {

    OrderItem orderItem;
}
