package com.loading.service.service;

import com.loading.service.domain.Box;
import com.loading.service.domain.Item;
import com.loading.service.dto.BoxRequest;
import com.loading.service.dto.ItemDTO;

import java.util.List;

public interface LoadingService {
    Box addBox(BoxRequest request);
    List<Item> loadItems(String txref, List<ItemDTO> dtos);
    List<Item> getItems(String txref);
    List<Box> getAvailableBoxes();
    int getBatteryLevel(String txref);
}
