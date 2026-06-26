package org.pablodiaz.encryption.controller;

import lombok.extern.log4j.Log4j2;
import org.pablodiaz.encryption.crypto.EncryptionResult;
import org.pablodiaz.encryption.dto.DecryptRequestDto;
import org.pablodiaz.encryption.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Log4j2
@RestController
@RequestMapping("/encryption")
public class EncryptionController {

    @Autowired
    private EncryptionService service;


    @PostMapping("/encrypt")
    public ResponseEntity<EncryptionResult> encrypt(
            @RequestParam MultipartFile file,
            @RequestParam String password) {

        log.info("calling encrypt({})", file.getOriginalFilename());

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            EncryptionResult result =
                    service.encryptFile(file, password);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<?> decrypt(@ModelAttribute DecryptRequestDto request){
        log.info("calling endpoint decrypt({},{},{})",request.getEncryptData(),request.getIv(), request.getSalt());
        try {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + request.getFilename() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(service.decrypt(request));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
