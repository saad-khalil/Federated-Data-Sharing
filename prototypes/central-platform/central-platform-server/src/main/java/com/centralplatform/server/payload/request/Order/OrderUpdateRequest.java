package com.centralplatform.server.payload.request.Order;

import com.centralplatform.server.model.Order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderUpdateRequest extends OrderRequest {

    Order order;
}
