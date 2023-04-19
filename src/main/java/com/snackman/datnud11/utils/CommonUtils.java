package com.snackman.datnud11.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;

public class CommonUtils {
    public static String getRandomPassword() {
        char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*")).toCharArray();
        return RandomStringUtils.random( 15, 0, possibleCharacters.length-1, false, false, possibleCharacters, new SecureRandom() );
    }
}
