package com.loading.service.service;

import com.loading.service.dto.BoxDTO;
import com.loading.service.dto.BoxRequest;
import com.loading.service.dto.ItemDTO;

import java.util.List;
import java.util.Map;

public interface LoadingService {
    BoxDTO addBox(BoxRequest request);
    List<ItemDTO> loadItems(String txref, List<ItemDTO> dtos);
    List<ItemDTO> getItems(String txref);
    List<BoxDTO> getBoxes();
    Map<String, Object> getBatteryLevel(String txref);
}
