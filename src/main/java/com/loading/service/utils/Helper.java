package com.loading.service.utils;

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
}
