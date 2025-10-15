package com.loading.service.utils;

import com.loading.service.domain.BatteryStatus;

import java.security.SecureRandom;

public class Helper {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRef(int length) {
        StringBuilder ref = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            ref.append(CHARACTERS.charAt(index));
        }
        return ref.toString();
    }


    public static BatteryStatus mapBatteryLevel(int level) {
        if (level >= 81) return BatteryStatus.FULL;
        else if (level >= 61) return BatteryStatus.HIGH;
        else if (level >= 41) return BatteryStatus.MEDIUM;
        else if (level >= 21) return BatteryStatus.LOW;
        else return BatteryStatus.CRITICAL;
    }
}
