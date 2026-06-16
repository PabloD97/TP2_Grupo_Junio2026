package org.pablodiaz.encryption.controller;

import lombok.extern.log4j.Log4j2;
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
    public ResponseEntity<?> encrypt(@RequestParam MultipartFile file,
                                                    @RequestParam String password){
        log.info("calling endpoint encrypt({})",file.getOriginalFilename());
        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("File is empty");
        }
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getOriginalFilename() + ".enc\"")
                    .body(service.encryptFile(file.getBytes(), password));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<?> decrypt(@RequestBody DecryptRequestDto request){
        log.info("calling endpoint decrypt({},{},{})",request.getEncryptData(),request.getIv(), request.getSalt());
        try {
            return ResponseEntity.ok(service.decrypt(request));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
