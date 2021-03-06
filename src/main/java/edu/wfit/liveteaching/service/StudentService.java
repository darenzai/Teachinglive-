package edu.wfit.liveteaching.service;

import edu.wfit.liveteaching.config.SecurityConfig;
import edu.wfit.liveteaching.dao.StudentDao;
import edu.wfit.liveteaching.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private SecurityConfig securityConfig;

    public Student findById(String id) {
        return studentDao.findById(id).orElse(null);
    }

    public Student findByIdAndPassword(String id, String password) {
        password = DigestUtils.md5DigestAsHex((password + securityConfig.getMd5salt()).getBytes());
        return studentDao.findByIdAndPassword(id, password).orElse(null);
    }

    public void save(Student student) {
        String password = DigestUtils.md5DigestAsHex((student.getPassword() + securityConfig.getMd5salt()).getBytes());
        student.setPassword(password);
        studentDao.save(student);
    }
}
