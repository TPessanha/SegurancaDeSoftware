package Util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by tomas on 12/11/2017.
 */
public class CryptoUtil {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[]{'F', 'C', 'T', '/', 'U', 'N', 'L', 'r',
            'o', 'c', 'k', 's', '!', '!', 'd', 'i'};
    Key key = new SecretKeySpec(keyValue, ALGO);

    /**
     * Encrypts a String value
     *
     * @param Data A String value to encrypt
     * @return A String encrypted
     * @throws Exception
     */
    public static String encrypt(String Data) throws Exception {
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        return java.util.Base64.
                getEncoder().encodeToString(encVal);
    }

    /**
     * Decrypts a String value
     *
     * @param encrypted Encrypted String to decrypt
     * @return Decrypted String
     * @throws Exception
     */
    public static String decrypt(String encrypted) throws Exception {
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = java.util.Base64.
                getDecoder().decode(encrypted);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }
}
