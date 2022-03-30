package edu.wfit.liveteaching.dao;

import edu.wfit.liveteaching.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDao extends JpaRepository<Course, String> {
}
