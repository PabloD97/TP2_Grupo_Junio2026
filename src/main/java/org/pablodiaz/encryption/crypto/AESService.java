package org.pablodiaz.encryption.crypto;

import lombok.extern.log4j.Log4j2;
import org.pablodiaz.encryption.exception.EncryptionException;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Log4j2
@Service
public class AESService {

    private static final int IV_LENGTH=12;
    private static final int TAG_LENGTH=128;

    public EncryptionResult encrypt(byte[] data, SecretKey key, byte[] salt) throws EncryptionException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        log.info("calling method encrypt()");
        try {
            SecureRandom random = new SecureRandom();

            byte[] iv = new byte[IV_LENGTH];
            random.nextBytes(iv);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

            byte[] encryptedData = cipher.doFinal(data);

            String encryptString = Base64.getEncoder().encodeToString(encryptedData);
            String ivString = Base64.getEncoder().encodeToString(iv);
            String saltString = Base64.getEncoder().encodeToString(salt);
            return new EncryptionResult(encryptString, ivString, saltString);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public byte[] decrypt(byte[] encryptedData, SecretKey key, byte[] iv) throws EncryptionException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        log.info("calling method decrypt()");
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);

            cipher.init(Cipher.DECRYPT_MODE, key, spec);

            return cipher.doFinal(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
