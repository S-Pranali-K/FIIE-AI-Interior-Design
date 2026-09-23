package com.file.backend.controller;

import com.file.backend.entity.RoomImage;
import com.file.backend.service.RoomImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class RoomImageController {

    private final RoomImageService roomImageService;

    public RoomImageController(RoomImageService roomImageService) {
        this.roomImageService = roomImageService;
    }

    // =========================================
    // UPLOAD ROOM IMAGE
    // =========================================

    @PostMapping("/{projectId}/images")
    public ResponseEntity<?> uploadImage(
            @PathVariable Long projectId,
            @RequestParam("file") MultipartFile file) {

        System.out.println("======================================");
        System.out.println("IMAGE UPLOAD REQUEST RECEIVED");
        System.out.println("PROJECT ID = " + projectId);

        if (file != null) {

            System.out.println(
                    "FILE NAME = " + file.getOriginalFilename()
            );

            System.out.println(
                    "FILE SIZE = " + file.getSize() + " bytes"
            );

            System.out.println(
                    "FILE TYPE = " + file.getContentType()
            );

            System.out.println(
                    "FILE EMPTY = " + file.isEmpty()
            );

        } else {

            System.out.println("FILE = NULL");
        }

        try {

            RoomImage roomImage =
                    roomImageService.uploadImage(
                            projectId,
                            file
                    );

            System.out.println("IMAGE SAVED SUCCESSFULLY");

            System.out.println(
                    "IMAGE ID = " + roomImage.getId()
            );

            System.out.println(
                    "IMAGE PATH = " + roomImage.getImagePath()
            );

            // =========================================
            // SAFE RESPONSE
            // =========================================

            Map<String, Object> response =
                    new HashMap<>();

            response.put(
                    "message",
                    "Image uploaded successfully"
            );

            response.put(
                    "imageId",
                    roomImage.getId()
            );

            response.put(
                    "projectId",
                    projectId
            );

            response.put(
                    "imagePath",
                    roomImage.getImagePath()
            );

            response.put(
                    "uploadedAt",
                    roomImage.getUploadedAt()
            );

            System.out.println(
                    "SAFE JSON RESPONSE CREATED"
            );

            System.out.println("======================================");

            return ResponseEntity.ok(response);

        } catch (IOException e) {

            System.out.println("IMAGE SAVE IO ERROR");

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body(
                            "Failed to save image: "
                                    + e.getMessage()
                    );

        } catch (RuntimeException e) {

            System.out.println("IMAGE SAVE RUNTIME ERROR");

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(
                            e.getMessage()
                    );
        }
    }


    // =========================================
    // GET PROJECT IMAGES
    // =========================================

    @GetMapping("/{projectId}/images")
    public ResponseEntity<List<Map<String, Object>>> getProjectImages(
            @PathVariable Long projectId) {

        List<RoomImage> images =
                roomImageService.getImagesByProject(projectId);

        List<Map<String, Object>> response =
                new ArrayList<>();

        for (RoomImage image : images) {

            Map<String, Object> imageData =
                    new HashMap<>();

            imageData.put(
                    "imageId",
                    image.getId()
            );

            imageData.put(
                    "projectId",
                    projectId
            );

            imageData.put(
                    "imagePath",
                    image.getImagePath()
            );

            imageData.put(
                    "uploadedAt",
                    image.getUploadedAt()
            );

            response.add(imageData);
        }

        System.out.println(
                "GET IMAGES FOR PROJECT ID = "
                        + projectId
        );

        System.out.println(
                "NUMBER OF IMAGES = "
                        + response.size()
        );

        return ResponseEntity.ok(response);
    }
}