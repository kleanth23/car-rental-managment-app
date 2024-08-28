package com.car_rental_managment_app.services;

import com.car_rental_managment_app.configs.UploadConfig;
import com.car_rental_managment_app.entities.ImageEntity;
import com.car_rental_managment_app.entities.ModelMapperUtils;
import com.car_rental_managment_app.repository.ImageRepo;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class ImageService {
    @Autowired
    UploadConfig uploadConfig;
    @Autowired
    ImageRepo imageRepo;

    public ImageEntity uploadImage(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = System.currentTimeMillis() + "_" + generateRandomFileName() + "." + fileExtension;

        File uploadDir = new File(uploadConfig.getUploadDirectory());
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            Path filePath = Path.of(uploadConfig.getUploadDirectory(), uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.out.println("e " + e);
            throw new RuntimeException();
        }

        ImageEntity image = new ImageEntity();
        image.setFileName(uniqueFileName);
        image.setOriginalFileName(originalFileName);
        String baseUrl = generateImageAbsolutePath();
        image.setUrl(baseUrl);
        imageRepo.save(image);
        return ModelMapperUtils.map(image, ImageEntity.class);
    }

    public String generateImageAbsolutePath() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .toUriString();
    }

    public ImageEntity getImageById(String imageId) {
        return imageRepo.findById(Long.valueOf(imageId)).orElseThrow(EntityNotFoundException::new);
    }

    private String generateRandomFileName() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
