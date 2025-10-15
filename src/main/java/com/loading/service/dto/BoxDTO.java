package com.loading.service.dto;


import com.loading.service.domain.BatteryStatus;
import com.loading.service.domain.Box;
import com.loading.service.domain.BoxStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

    public static BatteryStatus mapBatteryLevel(int level) {
        if (level >= 81) return BatteryStatus.FULL;
        else if (level >= 61) return BatteryStatus.HIGH;
        else if (level >= 41) return BatteryStatus.MEDIUM;
        else if (level >= 21) return BatteryStatus.LOW;
        else return BatteryStatus.CRITICAL;
    }

}
