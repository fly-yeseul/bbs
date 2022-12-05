package dev.fly_yeseul.bbs1.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtil {
    private CryptoUtil() {

    }

    public static String hashSha512(String input){
        // java sha512 이렇게 매번 구글링해서 그냥 이용하기
        try {
            // 이해하려 하지 않는 편이 좋다.
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.reset();
            md.update(input.getBytes(StandardCharsets.UTF_8));
            return String.format("%0125x", new BigInteger(1, md.digest()));
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }
}
