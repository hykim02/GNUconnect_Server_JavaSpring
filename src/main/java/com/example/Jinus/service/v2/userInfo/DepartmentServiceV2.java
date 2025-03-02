package com.example.Jinus.service.v2.userInfo;

import com.example.Jinus.repository.v2.userInfo.DepartmentRepositoryV2;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceV2 {

    private final DepartmentRepositoryV2 departmentRepository;

    public DepartmentServiceV2(DepartmentRepositoryV2 departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // 학과 영문명 찾기
    public String getDepartmentEng(int departmentId) {
        return departmentRepository.findDepartmentEngById(departmentId);
    }
}
