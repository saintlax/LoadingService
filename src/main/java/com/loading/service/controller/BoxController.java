package com.loading.service.controller;
import com.loading.service.domain.Box;
import com.loading.service.domain.Item;
import com.loading.service.dto.BoxDTO;
import com.loading.service.dto.BoxRequest;
import com.loading.service.dto.ItemDTO;
import com.loading.service.dto.ItemRequest;
import com.loading.service.service.LoadingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boxes")
public class BoxController {

    @Autowired
    LoadingService service;


    @PostMapping
    public ResponseEntity<BoxDTO> createBox(@Valid @RequestBody BoxRequest req) {
        Box box = service.addBox(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(BoxDTO.from(box));
    }

    @PostMapping("/{txref}/load")
    public ResponseEntity<List<ItemDTO>> loadBox(@PathVariable String txref, @Valid @RequestBody ItemRequest request) {
        List<Item> items = service.loadItems(txref, request.getItems());
        List<ItemDTO> dtos = items.stream().map(ItemDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{txref}/items")
    public ResponseEntity<List<ItemDTO>> getItems(@PathVariable String txref) {
        List<Item> items = service.getItems(txref);
        return ResponseEntity.ok(items.stream().map(ItemDTO::from).collect(Collectors.toList()));
    }

    @GetMapping("/available")
    public ResponseEntity<List<BoxDTO>> available() {
        List<BoxDTO> list = service.getAvailableBoxes().stream().map(BoxDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{txref}/battery")
    public ResponseEntity<Map<String, Object>> battery(@PathVariable String txref) {
        int battery = service.getBatteryLevel(txref);
        Map<String, Object> map = new HashMap<>();
        map.put("txref", txref);
        map.put("battery", battery);
        return ResponseEntity.ok(map);
    }
}
