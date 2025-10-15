package com.loading.service.controller;
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

@RestController
@RequestMapping("/api/boxes")
public class BoxController {

    @Autowired
    LoadingService service;


    @PostMapping("/addbox")
    public ResponseEntity<BoxDTO> addBox(@Valid @RequestBody BoxRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBox(req));
    }

    @PostMapping("/loadbox/{txref}")
    public ResponseEntity<List<ItemDTO>> loadBox(@PathVariable String txref, @Valid @RequestBody ItemRequest request) {
        return ResponseEntity.ok(service.loadItems(txref, request.getItems()));
    }

    @GetMapping("/getItems/{txref}")
    public ResponseEntity<List<ItemDTO>> getItems(@PathVariable String txref) {
        return ResponseEntity.ok(service.getItems(txref));
    }

    @GetMapping("/getAllAvailableBoxes")
    public ResponseEntity<List<BoxDTO>> availableBoxes() {
        return ResponseEntity.ok(service.getBoxes());
    }

    @GetMapping("/checkBattery/{txref}")
    public ResponseEntity<Map<String, Object>> battery(@PathVariable String txref) {
        return ResponseEntity.ok(service.getBatteryLevel(txref));
    }
}
