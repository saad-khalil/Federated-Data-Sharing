package com.centralplatform.server.payload.request.Inventory;

import com.centralplatform.server.model.Inventory.Inventory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InventoryUpdateRequest extends InventoryRequest {

   Inventory inventory;
}
