package edu.wfit.liveteaching.dao;

import edu.wfit.liveteaching.model.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorDao extends JpaRepository<Major, String> {
}
