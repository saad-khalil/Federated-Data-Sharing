package com.centralplatform.server.dto.Inventory;

import com.centralplatform.server.model.ItemType.ItemType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryDTO {

    ItemType itemType;
    int quantity;

    int capacity;
}
