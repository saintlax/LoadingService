package com.loading.service.service;

import com.loading.service.domain.BatteryStatus;
import com.loading.service.domain.Box;
import com.loading.service.domain.Item;
import com.loading.service.domain.BoxStatus;
import com.loading.service.dto.BoxDTO;
import com.loading.service.dto.BoxRequest;
import com.loading.service.dto.ItemDTO;
import com.loading.service.exception.NoItemException;
import com.loading.service.repository.BoxRepository;
import com.loading.service.repository.ItemRepository;
import com.loading.service.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.loading.service.utils.Helper.mapBatteryLevel;

@Service
public class Implementation  implements LoadingService {

    //this is not ideal because unit testing cannot be achieved
    // I had to do this because of time constraint
    @Autowired
    BoxRepository boxRepo;

    @Autowired
    ItemRepository itemRepo;

    @Override
    public BoxDTO addBox(BoxRequest request) {
        Box b = new Box();
        b.setTxref(request.getTxref() == null ? Helper.generateRef(8) : request.getTxref());
        b.setWeightLimit(request.getWeightLimit());
        b.setBattery(request.getBattery());
        b.setState(request.getBoxStatus() == null ? BoxStatus.IDLE : request.getBoxStatus());
        b = boxRepo.save(b);
        return BoxDTO.from(b);
    }

    @Override
    public List<ItemDTO> loadItems(String txref, List<ItemDTO> dtos) {
        Box box = getBoxByRef(txref);
        if (box.getBattery() < 25) {
            throw new IllegalStateException("Battery level is less than 25% and cannot start loading");
        }
        int currentWeight = itemRepo.findByBoxTxref(txref).stream().mapToInt(Item::getWeight).sum();
        int incomingWeight = dtos.stream().mapToInt(ItemDTO::getWeight).sum();

        if (currentWeight + incomingWeight > box.getWeightLimit()) {
            throw new IllegalStateException("Exceeds box weight limit");
        }

        List<String> codes = dtos.stream().map(ItemDTO::getCode).collect(Collectors.toList());
        List<Item> items = itemRepo.findByCodeIn(codes);
        if (!items.isEmpty()) {
            throw new IllegalArgumentException("Duplicate items codes found");
        }
        box.setState(BoxStatus.LOADING);
        boxRepo.save(box);

        List<Item> saved = new ArrayList<>();
        for (ItemDTO dto : dtos) {
            Item item = new Item();
            item.setName(dto.getName());
            item.setWeight(dto.getWeight());
            item.setCode(dto.getCode());
            item.setBox(box);
            saved.add(item);
        }
        itemRepo.saveAll(saved);
        box.setState(BoxStatus.LOADED);
        boxRepo.save(box);
        return saved.stream().map(ItemDTO::from).collect(Collectors.toList());
    }

    @Override
    public List<ItemDTO> getItems(String txref) {
        getBoxByRef(txref);//just for validation purpose
        return itemRepo.findByBoxTxref(txref).stream().map(ItemDTO::from).collect(Collectors.toList());
    }

    @Override
    public List<BoxDTO> getBoxes() {
        List<Box> boxes = boxRepo.findByBatteryGreaterThanEqualAndState(25, BoxStatus.IDLE);
        return boxes.stream().map(BoxDTO::from).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getBatteryLevel(String txref) {
        Box box = getBoxByRef(txref);
        Map<String, Object> map = new HashMap<>();
        map.put("txref", box.getTxref());
        map.put("meter", box.getBattery());
        map.put("level", mapBatteryLevel(box.getBattery()));
        return map;
    }

    private Box getBoxByRef(String txref) {
        return boxRepo.findDistinctFirstByTxref(txref).orElseThrow(() -> new NoItemException("Box not found: " + txref));
    }

}
