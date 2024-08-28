package com.car_rental_managment_app.endpoints;

import com.car_rental_managment_app.entities.ImageEntity;
import com.car_rental_managment_app.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")

public class ImageEndpoint {
    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageEntity> uploadImage(@RequestParam("image") MultipartFile file) {
        ImageEntity imageEntity = imageService.uploadImage(file);
        return ResponseEntity.ok(imageEntity);
    }
}
