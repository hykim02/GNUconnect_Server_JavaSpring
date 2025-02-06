package com.example.Jinus.repository.cafeteria;

import com.example.Jinus.entity.cafeteria.CafeteriaEntity;
import com.example.Jinus.repository.v2.cafeteria.CafeteriaRepositoryV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")  // application-test.properties 적용
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)  // H2 사용
public class CafeteriaRepositoryV2Test {

    @Autowired
    private CafeteriaRepositoryV2 cafeteriaRepositoryV2;

    @Test
    @DisplayName("사용자 캠퍼스에 해당하는 식당리스트 찾기")
    public void checkCafeteriaNameAndUrl() {
        // given
        int id = 1;
        String cafeteriaName = "아람관";
        int campusId = 2;
        String url = "https://www.cafeteria.com";
        int userCampusId = 2;

        // 예상 데이터
        List<Object[]> resultList = new ArrayList<>();
        Object[] row1 = {cafeteriaName, url};
        resultList.add(row1);

        CafeteriaEntity cafeteriaEntity = new CafeteriaEntity(id, cafeteriaName, campusId, url);
        cafeteriaRepositoryV2.save(cafeteriaEntity);

        // when
        List<Object[]> result = cafeteriaRepositoryV2.findCafeteriaListByCampusId(userCampusId);

        // then
        // 객체이므로 isEqualTo만 사용하면 메모리 주소를 비교함
        assertThat(result).usingRecursiveComparison().isEqualTo(resultList);
    }
}
