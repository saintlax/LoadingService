package com.loading.service.repository;

import com.loading.service.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByBoxTxref(String txref);
    Optional<Item> findByCode(String code);

    List<Item> findByCodeIn(List<String> codes);
}