package me.project.funding.commons;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256 알고리즘으로 문자를 암호화 한다.
 * 암호화 실패시 RuntimeException 을 던진다.
 * StringBuilder 는 멀티쓰레드 환경에 안전하지 않지만 성능적으로 우수하다,
 * 멀티쓰레드 환경에서 동기화가 필요하다면 StringBuffer 를 사용
 */
public class SHA256Util {
    public static final String ENCRYPTION_TYPE = "SHA-256";

    public static String encryptionSHA256(String str) {
        String SHA = null;

        MessageDigest sh;
        try {
            sh = MessageDigest.getInstance(ENCRYPTION_TYPE);
            sh.update(str.getBytes());
            byte[] byteData = sh.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : byteData) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 에러!", e);
        }
        return SHA;
    }

}
