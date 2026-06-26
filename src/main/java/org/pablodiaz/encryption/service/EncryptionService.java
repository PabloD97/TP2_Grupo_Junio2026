package org.pablodiaz.encryption.service;


import org.pablodiaz.encryption.crypto.EncryptionResult;
import org.pablodiaz.encryption.dto.DecryptRequestDto;
import org.springframework.web.multipart.MultipartFile;

public interface EncryptionService {
    EncryptionResult encryptFile(MultipartFile fileBytes, String password) throws Exception;
    byte[] decrypt(DecryptRequestDto decryptRequest) throws Exception;

}
