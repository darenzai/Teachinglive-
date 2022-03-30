package edu.wfit.liveteaching.dao;

import edu.wfit.liveteaching.model.VideoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoMaterialDao extends JpaRepository<VideoMaterial, String> {
    Optional<VideoMaterial> findByVideoId(String videoId);
}
