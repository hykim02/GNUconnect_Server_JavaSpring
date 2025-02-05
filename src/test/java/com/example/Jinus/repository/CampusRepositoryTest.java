package com.example.Jinus.repository;

import com.example.Jinus.repository.v2.cafeteria.CampusRepositoryV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CampusRepositoryTest {

    @Autowired
    private CampusRepositoryV2 campusRepository;

    @Test
    @DisplayName("빈 등록 테스트")
    public void CampusRepositoryIsNotNull() {
        assertThat(campusRepository).isNotNull();
    }
}
