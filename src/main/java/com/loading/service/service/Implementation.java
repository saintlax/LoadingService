package com.loading.service.service;

import com.loading.service.domain.Box;
import com.loading.service.domain.Item;
import com.loading.service.domain.Status;
import com.loading.service.dto.BoxRequest;
import com.loading.service.dto.ItemDTO;
import com.loading.service.exception.NoItemException;
import com.loading.service.repository.BoxRepository;
import com.loading.service.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Implementation  implements LoadingService {

    @Autowired
    BoxRepository boxRepo;
    @Autowired
    ItemRepository itemRepo;

    @Override
    public Box addBox(BoxRequest request) {
        Box b = new Box();
        b.setTxref(request.getTxref());
        b.setWeightLimit(request.getWeightLimit());
        b.setBattery(request.getBattery());
        b.setState(request.getStatus() == null ? Status.IDLE : request.getStatus());
        return boxRepo.save(b);
    }

    @Override
    public List<Item> loadItems(String txref, List<ItemDTO> dtos) {
        Box box = getBox(txref);
        if (box.getBattery() < 25) {
            throw new IllegalStateException("Box battery below 25% — cannot start loading");
        }

        int currentWeight = itemRepo.findByBoxTxref(txref).stream().mapToInt(Item::getWeight).sum();
        int incomingWeight = dtos.stream().mapToInt(ItemDTO::getWeight).sum();

        if (currentWeight + incomingWeight > box.getWeightLimit()) {
            throw new IllegalStateException("Exceeds box weight limit");
        }

        box.setState(Status.LOADING);
        boxRepo.save(box);

        List<Item> saved = new ArrayList<>();
        List<String> codes = dtos.stream().map(ItemDTO::getCode).collect(Collectors.toList());
        List<Item> items = itemRepo.findByCodeIn(codes);
        if (!items.isEmpty()) {
            throw new IllegalArgumentException("Duplicate items codes found");
        }
        for (ItemDTO dto : dtos) {
//            if (itemRepo.findByCode(dto.getCode()).isPresent()) {
//                throw new IllegalArgumentException("Item code already exists: " + dto.getCode());
//            }
            Item item = new Item();
            item.setName(dto.getName());
            item.setWeight(dto.getWeight());
            item.setCode(dto.getCode());
            item.setBox(box);
            saved.add(item);
        }
        itemRepo.saveAll(saved);
        box.setState(Status.LOADED);
        boxRepo.save(box);
        return saved;
    }

    @Override
    public List<Item> getItems(String txref) {
        getBox(txref);
        return itemRepo.findByBoxTxref(txref);
    }

    @Override
    public List<Box> getAvailableBoxes() {
        return boxRepo.findAll().stream()
                .filter(b -> b.getBattery() >= 25 && b.getState() == Status.IDLE)
                .collect(Collectors.toList());
    }

    @Override
    public int getBatteryLevel(String txref) {
        Box box = getBox(txref);
        return box.getBattery();
    }

    private Box getBox(String txref) {
        return boxRepo.findDistinctFirstByTxref(txref).orElseThrow(() -> new NoItemException("Box not found: " + txref));
    }
}
