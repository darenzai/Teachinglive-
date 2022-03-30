package edu.wfit.liveteaching.dao;

import edu.wfit.liveteaching.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentDao extends JpaRepository<Student, String> {
    Optional<Student> findByIdAndPassword(String id, String password);
}
