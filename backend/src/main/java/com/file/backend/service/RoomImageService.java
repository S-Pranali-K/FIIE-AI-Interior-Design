package com.file.backend.service;

import com.file.backend.entity.Project;
import com.file.backend.entity.RoomImage;
import com.file.backend.repository.ProjectRepository;
import com.file.backend.repository.RoomImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RoomImageService {

    private final RoomImageRepository roomImageRepository;
    private final ProjectRepository projectRepository;

    private final Path uploadDirectory =
            Paths.get("uploads/room-images");

    public RoomImageService(
            RoomImageRepository roomImageRepository,
            ProjectRepository projectRepository) {

        this.roomImageRepository = roomImageRepository;
        this.projectRepository = projectRepository;
    }

    public RoomImage uploadImage(
            Long projectId,
            MultipartFile file) throws IOException {

        // Find project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(
                        () -> new RuntimeException("Project not found")
                );

        // Validate file
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Image file is empty");
        }

        // Create upload directory
        Files.createDirectories(uploadDirectory);

        // Get original filename
        String originalFilename =
                file.getOriginalFilename();

        if (originalFilename == null ||
                originalFilename.isBlank()) {
            originalFilename = "room-image";
        }

        // Generate unique filename
        String fileName =
                UUID.randomUUID()
                        + "_"
                        + originalFilename;

        // Final file path
        Path filePath =
                uploadDirectory.resolve(fileName);

        // Save physical image
        Files.copy(
                file.getInputStream(),
                filePath
        );

        // Save database record
        RoomImage roomImage =
                new RoomImage(
                        project,
                        filePath.toString(),
                        LocalDateTime.now()
                );

        return roomImageRepository.save(roomImage);
    }

    public List<RoomImage> getImagesByProject(
            Long projectId) {

        return roomImageRepository
                .findByProjectId(projectId);
    }
}