package com.example.Jinus.service;

import com.example.Jinus.entity.DepartmentEntity;
import com.example.Jinus.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public int getCollegeId(int departmentId) {
        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).orElse(null);
        // 학과 찾기
        if (departmentEntity != null) {
            return departmentEntity.getCollegeId(); // 단과대학 id 리턴
        } else {
            return -1;
        }
    }

}
