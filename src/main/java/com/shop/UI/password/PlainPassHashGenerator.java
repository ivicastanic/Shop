package com.shop.UI.password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PlainPassHashGenerator {

    //mirsad123 -> 1000:509fd2d31e97251195802e416bb775de:4888ceed4a54346a0c4052bdcfdd8d865dda97281c2783a8c23d998884ec5c24c7928ddf68f6bde69b4a60c4d27ba411f35f5d2639fde687cf01711d658d7106
    public String generateHashedPassword(String plainPassword) {
        /**
         * Hashirat ćemo plain password ali ćemo dodati malo začina..
         * <p>
         *     plain password + salt će se hashirati u 1000 iteracija
         * </p>
         */
        try {
            int iterations = 1000;
            char[] plainPasswordCharArray = plainPassword.toCharArray();
            byte[] salt = getSalt();

            /**
             * passwordKeySpecification je varijabla/objekat koja čuva informacije o specifikaciji hashiranja moje lozinke.
             */
            PBEKeySpec passwordKeySpecification = new PBEKeySpec(plainPasswordCharArray, salt, iterations, 64 * 8);
            /**
             * SecretKeyFactory je tvornica ne čokolade nego tvornica kojoj dajemo ime algoritma hashiranja.
             * U našem slučaju to je "PBKDF2WithHmacSHA1" a njena statička funkcija nam za uzvrat vraća
             * varijablu  "SecretKeyFactory" koja će nam pomoći da konačno hashiranmo lozinku.
             */
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(PlainPassSupportedAlgorithm.HASH_ALGORITHM_NAME.getName());

            /**
             * secretKeyFactory posjeduje funkciju kojoj damo passwordKeySpecification a ona nam vrati niz bita
             koji u osnovi predstavlju našu verziju hashiranje lozinke
             **/
            byte[] hash = secretKeyFactory.generateSecret(passwordKeySpecification).getEncoded();
            return iterations + ":" + toHex(salt) + ":" + toHex(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new RuntimeException(exception);
        }

    }

    //"mdsmskdmsm"
    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
