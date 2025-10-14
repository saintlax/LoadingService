package com.loading.service.repository;

import com.loading.service.domain.Box;
import com.loading.service.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box, Long> {
    List<Box> findByBatteryGreaterThanEqualAndState(int battery, Status status);

    Optional<Box> findDistinctFirstByTxref(String txref);
}