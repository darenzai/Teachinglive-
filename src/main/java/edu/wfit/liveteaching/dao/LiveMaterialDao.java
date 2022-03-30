package edu.wfit.liveteaching.dao;

import edu.wfit.liveteaching.model.LiveMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiveMaterialDao extends JpaRepository<LiveMaterial, String> {
    Optional<LiveMaterial> findByLiveId(String liveId);
}
