package com.example.Jinus.service;

import com.example.Jinus.entity.TestEntity;
import com.example.Jinus.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class TestService {
    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<TestEntity> getAllNotices() {
        return testRepository.findAll();
    }
}
