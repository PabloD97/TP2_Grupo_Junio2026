package org.pablodiaz.encryption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.pablodiaz.encryption.crypto.AESService;
import org.pablodiaz.encryption.crypto.EncryptionResult;
import org.pablodiaz.encryption.crypto.KeyGeneratorService;
import org.pablodiaz.encryption.dto.DecryptRequestDto;
import org.pablodiaz.encryption.exception.EncryptionException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.SecureRandom;

@RequiredArgsConstructor
@Log4j2
@Service("EncryptionServiceImpl")
public class EncryptionServiceImpl implements EncryptionService{

    private final AESService aesService;
    private final KeyGeneratorService  keyGeneratorService;

    @Override
    public EncryptionResult encryptFile(byte[] fileBytes, String password) throws Exception {
        log.info("Encrypt file requested. Size={} bytes",
                fileBytes.length);
        try{
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            SecretKey secretKey = keyGeneratorService.generateKey(password, salt);
            log.info("Key generated successfully");
            return aesService.encrypt(fileBytes, secretKey, salt);
        }catch (Exception error){
            log.error("error while encrypting file {} with message {}",fileBytes,error.getMessage());
            throw new EncryptionException(error.getMessage(), error);
        }
    }

    @Override
    public byte[] decrypt(DecryptRequestDto decryptRequest) throws Exception {
        log.info("calling decrypt()");

        try {
            SecretKey secretKey = keyGeneratorService.generateKey(decryptRequest.getPassword(), decryptRequest.getSaltBytes());
            log.info("Key generated successfully");
            return aesService.decrypt(decryptRequest.getEncryptDataBytes(), secretKey, decryptRequest.getIvBytes());
        } catch (EncryptionException error) {
            log.error("error while decrypt file {} with message {}",decryptRequest.getEncryptData(),error.getMessage());
            throw new EncryptionException("Error encrypting file",error);
        }
    }

}
