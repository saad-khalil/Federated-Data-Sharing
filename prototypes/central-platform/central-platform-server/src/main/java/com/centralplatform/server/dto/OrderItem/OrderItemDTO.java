package com.centralplatform.server.dto.OrderItem;

import com.centralplatform.server.model.ItemType.ItemType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    ItemType itemType;
    int requestedQuantity;
    int actualQuantity;
}
