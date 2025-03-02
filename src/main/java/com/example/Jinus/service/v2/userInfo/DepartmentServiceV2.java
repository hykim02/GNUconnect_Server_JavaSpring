package com.example.Jinus.service.v2.userInfo;

import com.example.Jinus.repository.userInfo.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceV2 {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceV2(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
}
