package com.example.votingbank.service;

import com.example.votingbank.util.EncryptionUtil;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final byte[] key = EncryptionUtil.generateKey(); // In production, use a secure key management system

    public byte[] encrypt(String data) {
        return EncryptionUtil.encrypt(data, key).getBytes();
    }

    public String decrypt(byte[] data) {
        return EncryptionUtil.decrypt(new String(data), key);
    }
}
