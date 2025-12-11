package com.example.votingbank.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtil {

    private static final String AES = "AES";
    private static final String AES_GCM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;

    private EncryptionUtil() {
        // private constructor to prevent instantiation
    }

    /**
     * Encrypts the given plain text using AES-GCM.
     *
     * @param plainText plain text
     * @param key       16-byte key
     * @return Base64 encoded ciphertext
     */
    public static String encrypt(String plainText, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_GCM);
            byte[] iv = new byte[12]; // 12 bytes IV for GCM
            new SecureRandom().nextBytes(iv);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            SecretKey secretKey = new SecretKeySpec(key, AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    /**
     * Decrypts AES-GCM ciphertext.
     *
     * @param cipherText Base64 encoded ciphertext
     * @param key        16-byte key
     * @return decrypted plain text
     */
    public static String decrypt(String cipherText, byte[] key) {
        try {
            byte[] decoded = Base64.getDecoder().decode(cipherText);
            byte[] iv = new byte[12];
            System.arraycopy(decoded, 0, iv, 0, iv.length);
            byte[] encrypted = new byte[decoded.length - iv.length];
            System.arraycopy(decoded, iv.length, encrypted, 0, encrypted.length);
            Cipher cipher = Cipher.getInstance(AES_GCM);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            SecretKey secretKey = new SecretKeySpec(key, AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            return new String(cipher.doFinal(encrypted));
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }

    /**
     * Generates a random AES 128-bit key.
     *
     * @return byte array of key
     */
    public static byte[] generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(AES);
            keyGen.init(128);
            SecretKey key = keyGen.generateKey();
            return key.getEncoded();
        } catch (Exception e) {
            throw new RuntimeException("Key generation error", e);
        }
    }
}
