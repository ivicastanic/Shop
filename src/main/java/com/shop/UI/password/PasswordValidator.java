package com.shop.UI.password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordValidator {

    public boolean validatePassword(String originalPassword, String storedPassword) {
        try {
            String[] parts = storedPassword.split(":");// 1000 :   salt hashed : pass spec hashed
            int iterations = Integer.parseInt(parts[0]);

            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            PBEKeySpec pbeKeySpecPlainPassword = new PBEKeySpec(originalPassword.toCharArray(),
                    salt, iterations, hash.length * 8);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PlainPassSupportedAlgorithm.HASH_ALGORITHM_NAME.getName());
            byte[] testHash = secretKeyFactory.generateSecret(pbeKeySpecPlainPassword).getEncoded();

            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff = diff | hash[i] ^ testHash[i];
            }
            return diff == 0;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            return false;
        }
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
