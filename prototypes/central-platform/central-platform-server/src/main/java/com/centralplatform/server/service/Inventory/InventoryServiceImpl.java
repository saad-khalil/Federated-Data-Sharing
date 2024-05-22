package com.centralplatform.server.service.Inventory;

import com.centralplatform.server.dto.Inventory.InventoryDTO;
import com.centralplatform.server.dto.Inventory.InventoryDTOConverter;
import com.centralplatform.server.model.Inventory.Inventory;
import com.centralplatform.server.payload.request.Inventory.InventoryRequest;
import com.centralplatform.server.repository.Inventory.InventoryRepository;
import com.centralplatform.server.service.Inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryDTOConverter converter;

    @Override
    public List<InventoryDTO> getInventorys(InventoryRequest request) {
        return converter.convert(inventoryRepository.findAll());
    }

    @Override
    public InventoryDTO getInventoryById(String id) {
        return converter.convert(inventoryRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public InventoryDTO uploadInventory(InventoryRequest request) {
        Inventory inventory = Inventory.builder().build();
        return converter.convert(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryDTO updateInventory(InventoryRequest request, String id) {
        return null;
    }

    @Override
    public List<InventoryDTO> uploadInventorys(InventoryRequest request) {
        List<Inventory> inventoryList = new ArrayList<>();
        return converter.convert(inventoryList);
    }

    @Override
    public void deleteInventoryById(String id) {
        inventoryRepository.delete(inventoryRepository.findById(UUID.fromString(id)).orElseThrow());
    }

    @Override
    public void deleteInventorys(InventoryRequest request) {
        List<UUID> inventoryIdList = new ArrayList<>();
        inventoryRepository.deleteAll(inventoryRepository.findAllById(inventoryIdList));
    }
}
