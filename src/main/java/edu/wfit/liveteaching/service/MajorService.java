package edu.wfit.liveteaching.service;

import edu.wfit.liveteaching.dao.MajorDao;
import edu.wfit.liveteaching.model.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {
    @Autowired
    private MajorDao majorDao;

    public List<Major> findAll() {
        return majorDao.findAll();
    }

    public Major findById(String id) {
        return majorDao.findById(id).orElse(null);
    }
}
