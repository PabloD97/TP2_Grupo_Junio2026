package org.pablodiaz.encryption.dto;

import lombok.Data;

import java.util.Base64;

@Data
public class DecryptRequestDto{

    private String encryptData;
    private String iv;
    private String salt;
    private String password;
    private String filename;

    private byte[] encryptDataBytes;
    private byte[] ivBytes;
    private byte[] saltBytes;

    DecryptRequestDto(String encryptData, String iv, String salt, String password, String filename) {
        this.encryptData = encryptData;
        this.iv = iv;
        this.salt = salt;
        this.password = password;
        this.filename = filename;

        this.ivBytes = Base64.getDecoder().decode(iv);
        this.saltBytes = Base64.getDecoder().decode(salt);
        this.encryptDataBytes = Base64.getDecoder().decode(encryptData);
    }

}
