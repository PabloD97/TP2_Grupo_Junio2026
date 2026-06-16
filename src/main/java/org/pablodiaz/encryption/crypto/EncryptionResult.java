package org.pablodiaz.encryption.crypto;

import lombok.Data;


public record EncryptionResult(String encryptData, String iv, String salt) {

}
