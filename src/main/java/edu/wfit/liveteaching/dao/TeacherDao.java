package edu.wfit.liveteaching.dao;

import edu.wfit.liveteaching.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, String> {
    Optional<Teacher> findByIdAndPassword(String id, String password);
}
