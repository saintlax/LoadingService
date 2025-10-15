package com.loading.service.repository;

import com.loading.service.domain.Box;
import com.loading.service.domain.BoxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box, Long> {
    List<Box> findByBatteryGreaterThanEqualAndState(int battery, BoxStatus boxStatus);

    Optional<Box> findDistinctFirstByTxref(String txref);

    @Query("SELECT b FROM Box b WHERE b.battery >= 25 AND b.state = :state")
    List<Box> findAvailableBoxes(@Param("state") BoxStatus state);

}