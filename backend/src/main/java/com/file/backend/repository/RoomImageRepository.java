package com.file.backend.repository;

import com.file.backend.entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomImageRepository
        extends JpaRepository<RoomImage, Long> {

    List<RoomImage> findByProjectId(Long projectId);
}