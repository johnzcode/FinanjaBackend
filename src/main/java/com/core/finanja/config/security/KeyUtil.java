package com.core.finanja.config.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtil {

    public static RSAPrivateKey getPrivateKey(String privateKeyPath) throws Exception {
        try (FileInputStream fis = new FileInputStream(privateKeyPath)) {
            byte[] keyBytes = fis.readAllBytes();
            String privateKeyContent = new String(keyBytes);
            privateKeyContent = privateKeyContent
                    .replaceAll("\\s+", "");

            byte[] decodedKey = Base64.getDecoder().decode(privateKeyContent);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (IOException e) {
            throw new Exception("Error reading the private key file", e);
        }
    }

    public static RSAPublicKey getPublicKey(String publicKeyPath) throws Exception {
        try (FileInputStream fis = new FileInputStream(publicKeyPath)) {
            byte[] keyBytes = fis.readAllBytes();
            String publicKeyContent = new String(keyBytes);
            publicKeyContent = publicKeyContent
                    .replaceAll("\\s+", "");

            byte[] decodedKey = Base64.getDecoder().decode(publicKeyContent);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (IOException e) {
            throw new Exception("Error reading the public key file", e);
        }
    }
}
