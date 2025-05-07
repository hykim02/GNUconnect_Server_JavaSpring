package com.example.Jinus.service.userInfo;

import com.example.Jinus.repository.userInfo.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    // 학과 영문명 찾기
    public String getDepartmentEng(int departmentId) {
        return departmentRepository.findDepartmentEngById(departmentId);
    }
}
