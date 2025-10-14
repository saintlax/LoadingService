package com.loading.service.dto;


import com.loading.service.domain.Box;
import com.loading.service.domain.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoxDTO {

    //practically the variables should not match the entity variable.. heck, this is a test..lol
    private String txref;
    private int weightLimit;
    private int battery;
    private Status state;

    public static BoxDTO from(Box b) {
        BoxDTO dto = new BoxDTO();
        dto.setTxref(b.getTxref());
        dto.setWeightLimit(b.getWeightLimit());
        dto.setBattery(b.getBattery());
        dto.setState(b.getState());
        return dto;
    }

}
