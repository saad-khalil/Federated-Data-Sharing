package com.centralplatform.server.service.Inventory;

import com.centralplatform.server.dto.Inventory.InventoryDTO;
import com.centralplatform.server.payload.request.Inventory.InventoryRequest;

import java.util.List;

public interface InventoryService<R extends InventoryRequest>{
    List<InventoryDTO> getInventorys(R request);

    InventoryDTO getInventoryById(String id);

    InventoryDTO uploadInventory(R request);

    InventoryDTO updateInventory(R request, String id);

    List<InventoryDTO> uploadInventorys(R request);

    void deleteInventoryById(String id);

    void deleteInventorys(R request);
}
