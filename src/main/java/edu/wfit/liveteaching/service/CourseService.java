package edu.wfit.liveteaching.service;

import edu.wfit.liveteaching.dao.CourseDao;
import edu.wfit.liveteaching.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseDao courseDao;

    public List<Course> findAll() {
        return courseDao.findAll();
    }

    public Course findById(String courseId) {
        return courseDao.findById(courseId).orElse(null);
    }
}
