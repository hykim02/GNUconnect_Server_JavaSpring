package com.example.Jinus.controller;

import com.example.Jinus.entity.DepartmentEntity;
import com.example.Jinus.entity.UserEntity;
import com.example.Jinus.service.CollegeService;
import com.example.Jinus.service.DepartmentService;
import com.example.Jinus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final CollegeService collegeService;
    private final UserService userService;
    private final DepartmentService departmentService;

    @Autowired
    public TestController(UserService userService,
                          CollegeService collegeService,
                          DepartmentService departmentService) {
        this.userService = userService;
        this.collegeService = collegeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/colleges")
    public List<String> getAllColleges() {
        return collegeService.findAllUsers();
    }


    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {
        return userService.findAllUsers();
    }



    @GetMapping("/departments")
    public List<DepartmentEntity> getAllDepartments() {
        return departmentService.findAllDepartments();
    }
}
