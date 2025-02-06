package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.repository.v2.cafeteria.CampusRepositoryV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")  // application-test.properties 적용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // H2 사용
public class CampusRepositoryV2Test {

    @Autowired
    private CampusRepositoryV2 campusRepository;

    @Test
    @DisplayName("사용자 캠퍼스 이름 찾기")
    public void checkUserCampusName() {
        // given
        int id = 1;
        int campusId = 1; // 사용자가 등록한 캠퍼스id
        String campusName = "가좌캠퍼스";
        String url = "https://www.campus.com";

        CampusEntity campusEntity = new CampusEntity(id, campusName, url);
        campusRepository.save(campusEntity);

        // when
        String result = campusRepository.findCampusNameById(campusId);
        System.out.println("result: " + result);

        // then
        assertThat(result).isEqualTo(campusName);
    }
}
