package org.pablodiaz.encryption.service;


import org.pablodiaz.encryption.crypto.EncryptionResult;
import org.pablodiaz.encryption.dto.DecryptRequestDto;

public interface EncryptionService {
    EncryptionResult encryptFile(byte[] fileBytes, String password) throws Exception;
    byte[] decrypt(DecryptRequestDto decryptRequest) throws Exception;

}
