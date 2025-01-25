package com.core.finanja.config.security;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {

    public static RSAPrivateKey getPrivateKey(String privateKeyPath) throws Exception {
        byte[] decodedKey;

        if (new File(privateKeyPath).exists()) {
            try (FileInputStream fis = new FileInputStream(privateKeyPath)) {
                byte[] keyBytes = fis.readAllBytes();
                String privateKeyContent = new String(keyBytes).replaceAll("\\s+", "");
                decodedKey = Base64.getDecoder().decode(privateKeyContent);
            }
        } else {
            String privateKeyContent = privateKeyPath.replaceAll("\\s+", "");
            decodedKey = Base64.getDecoder().decode(privateKeyContent);
        }

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    public static RSAPublicKey getPublicKey(String publicKeyPath) throws Exception {
        byte[] decodedKey;

        if (new File(publicKeyPath).exists()) {
            try (FileInputStream fis = new FileInputStream(publicKeyPath)) {
                byte[] keyBytes = fis.readAllBytes();
                String publicKeyContent = new String(keyBytes).replaceAll("\\s+", "");
                decodedKey = Base64.getDecoder().decode(publicKeyContent);
            }
        } else {
            String publicKeyContent = publicKeyPath.replaceAll("\\s+", "");
            decodedKey = Base64.getDecoder().decode(publicKeyContent);
        }

        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }
}
