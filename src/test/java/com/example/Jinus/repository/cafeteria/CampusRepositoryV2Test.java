package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.repository.v2.cafeteria.CampusRepositoryV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")  // application-test.properties 적용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // H2 사용
public class CampusRepositoryV2Test {

    @Autowired
    private CampusRepositoryV2 campusRepositoryV2;

    @BeforeEach
    void setUp() {
        campusRepositoryV2.save(new CampusEntity(1, "가좌", "url1"));
        campusRepositoryV2.save(new CampusEntity(2, "칠암", "url2"));
        campusRepositoryV2.save(new CampusEntity(5, "내동", "url3")); // id가 5라서 필터링 대상 아님
    }

    @Test
    @DisplayName("사용자 캠퍼스 이름 찾기")
    public void checkUserCampusName_usingCampusId() {
        // given
        int campusId = 1; // 사용자가 등록한 캠퍼스id
        String campusName = "가좌";

        // when
        String result = campusRepositoryV2.findCampusNameById(campusId);
        System.out.println("result: " + result);

        // then
        assertThat(result).isEqualTo(campusName);
    }

    @Test
    @DisplayName("campusId < 5 캠퍼스 찾기")
    public void checkCampusList_campusIdLessThan5() {
        // given - 예상 데이터
        List<CampusEntity> campusList = new ArrayList<>();
        campusList.add(new CampusEntity(1, "가좌", "url1"));
        campusList.add(new CampusEntity(2, "칠암", "url2"));

        // when
        List<CampusEntity> result = campusRepositoryV2.findCampusList();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).usingRecursiveComparison().isEqualTo(campusList);
    }
}
