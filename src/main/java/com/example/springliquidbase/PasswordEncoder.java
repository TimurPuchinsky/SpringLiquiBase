package com.example.springliquidbase;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

@Component
public class PasswordEncoder {

    public static final int ITERATIONS = 100;
    public static final int KEY_LENGTH = 40;

    public String encode(String password) {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf;
        byte[] hash = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            hash = skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toHex(salt) + ":" + toHex(hash);
    }

    public boolean matches(String password, String encodedPassword) {
        String[] parts = encodedPassword.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] hash = fromHex(parts[1]);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf;
        byte[] testHash = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return slowEquals(hash, testHash);
    }

    public String toHex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }

    public byte[] fromHex(String hex) {
        byte[] ret = new byte[hex.length()/2];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (byte) Integer.parseInt(hex.substring(i*2, i*2+2), 16);
        }
        return ret;
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}
