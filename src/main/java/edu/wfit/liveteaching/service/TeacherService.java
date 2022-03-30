package edu.wfit.liveteaching.service;

import edu.wfit.liveteaching.config.SecurityConfig;
import edu.wfit.liveteaching.dao.TeacherDao;
import edu.wfit.liveteaching.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private SecurityConfig securityConfig;

    public Teacher findById(String id) {
        return teacherDao.findById(id).orElse(null);
    }

    public Teacher findByIdAndPassword(String id, String password) {
        password = DigestUtils.md5DigestAsHex((password + securityConfig.getMd5salt()).getBytes());
        return teacherDao.findByIdAndPassword(id, password).orElse(null);
    }

    public void save(Teacher teacher) {
        String password = DigestUtils.md5DigestAsHex((teacher.getPassword() + securityConfig.getMd5salt()).getBytes());
        teacher.setPassword(password);
        teacherDao.save(teacher);
    }
}
