package com.senmash.inventory_service.service;


import com.senmash.inventory_service.dto.InventoryDTO;
import com.senmash.inventory_service.model.Inventory;
import com.senmash.inventory_service.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<InventoryDTO> getAllItems() {
        List<Inventory>itemList = inventoryRepository.findAll();
        return modelMapper.map(itemList, new TypeToken<List<InventoryDTO>>(){}.getType());
    }

    public InventoryDTO saveItem(InventoryDTO inventoryDTO) {
        inventoryRepository.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public InventoryDTO updateItem(InventoryDTO inventoryDTO) {
        inventoryRepository.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public String deleteItem(Integer itemId) {
        inventoryRepository.deleteById(itemId);
        return "Item deleted";
    }

    public InventoryDTO getItemById(Integer itemId) {
        Inventory item = inventoryRepository.getItemById(itemId);
        return modelMapper.map(item, InventoryDTO.class);
    }
}
