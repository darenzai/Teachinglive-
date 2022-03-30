package edu.wfit.liveteaching.dao;

import edu.wfit.liveteaching.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeworkDao extends JpaRepository<Homework, String> {
    List<Homework> findByStudentId(String studentId);

    List<Homework> findByVideoId(String videoId);

    Optional<Homework> findByStudentIdAndVideoId(String studentId, String videoId);
}
