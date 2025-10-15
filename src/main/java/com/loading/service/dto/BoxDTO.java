package com.loading.service.dto;


import com.loading.service.domain.BatteryStatus;
import com.loading.service.domain.Box;
import com.loading.service.domain.BoxStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.loading.service.utils.Helper.mapBatteryLevel;

@Getter
@Setter
@ToString
public class BoxDTO {

    private String txref;
    private int weightLimit;
    private BatteryStatus battery;
    private BoxStatus state;

    public static BoxDTO from(Box b) {
        BoxDTO dto = new BoxDTO();
        dto.setTxref(b.getTxref());
        dto.setWeightLimit(b.getWeightLimit());
        dto.setBattery(mapBatteryLevel(b.getBattery()));
        dto.setState(b.getState());
        return dto;
    }

}
