package org.pablodiaz.encryption.dto;

import lombok.Data;
import org.pablodiaz.encryption.crypto.EncryptionResult;

import java.util.Base64;

@Data
public class DecryptRequestDto{

    private String encryptData;
    private String iv;
    private String salt;
    private String password;

    private byte[] encryptDataBytes;
    private byte[] ivBytes;
    private byte[] saltBytes;

    DecryptRequestDto(String encryptData, String iv, String salt, String password) {
        this.encryptData = encryptData;
        this.iv = iv;
        this.salt = salt;
        this.password = password;

        this.ivBytes = Base64.getDecoder().decode(iv);
        this.saltBytes = Base64.getDecoder().decode(salt);
        this.encryptDataBytes = Base64.getDecoder().decode(encryptData);
    }

}
