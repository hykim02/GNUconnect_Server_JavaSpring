package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CafeteriaDietEntity;
import com.example.Jinus.repository.v2.cafeteria.DietRepositoryV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")  // application-test.properties 적용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // H2 사용
public class DietRepositoryV2Test {
    @Autowired
    private DietRepositoryV2 dietRepositoryV2;

    @BeforeEach
    void setUp() {
        // 날짜 + 시간 + 시간대 포함 (한국 시간: UTC+9)
        LocalDateTime dateTime = LocalDateTime.of(25, 2, 11, 0, 0, 0);
        CafeteriaDietEntity cafeteriaDietEntity =
                new CafeteriaDietEntity(1, dateTime, "월", "점심", "A코스", "주식", "쌀밥", 7);
        CafeteriaDietEntity cafeteriaDietEntity2 =
                new CafeteriaDietEntity(1, dateTime, "월", "점심", "B코스", "주식", "달걀", 7);
        dietRepositoryV2.save(cafeteriaDietEntity);
        dietRepositoryV2.save(cafeteriaDietEntity2);
    }

    @Test
    @DisplayName("조건에 맞는 식단 찾기")
    public void checkDiet_meetConditions() {
        //given
        String dateTime = "2025-03-09 00:00:00.000000 +09:00";
        String period = "점심";
        int cafeteriaId = 7;

        // 예상 데이터
        List<Object[]> dietList = new ArrayList<>();
        Object[] diet1 = {"A코스", "주식", "쌀밥"};
        Object[] diet2 = {"B코스", "주식", "달걀"};
        dietList.add(diet1);
        dietList.add(diet2);

        // when
        List<Object[]> result = dietRepositoryV2.findDietList(dateTime, period, cafeteriaId);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(dietList);
    }
}
