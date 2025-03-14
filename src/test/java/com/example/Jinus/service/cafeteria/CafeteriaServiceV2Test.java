package com.example.Jinus.service.cafeteria;

import com.example.Jinus.dto.data.CafeteriaDto;
import com.example.Jinus.repository.v2.cafeteria.CafeteriaRepositoryV2;
import com.example.Jinus.service.v2.cafeteria.CacheServiceV2;
import com.example.Jinus.service.v2.cafeteria.CafeteriaServiceV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CafeteriaServiceV2Test {

    @Autowired
    private CafeteriaServiceV2 cafeteriaServiceV2;
    @Autowired
    private CacheServiceV2 cacheServiceV2;
    @MockBean
    private CafeteriaRepositoryV2 cafeteriaRepositoryV2;

    @Test
    @DisplayName("사용자 캠퍼스id에 해당하는 식당리스트 찾기")
    public void checkUserCafeteriaList() {
        // given
        int campusId = 2;
        // 예상 데이터
        List<CafeteriaDto> resultList = new ArrayList<>();
        CafeteriaDto cafeteriaDto = new CafeteriaDto("아람관", "aram.com");
        resultList.add(cafeteriaDto);

        // when
        Mockito.when(cafeteriaRepositoryV2.findCafeteriaListByCampusId(campusId)).thenReturn(resultList);
        List<CafeteriaDto> result = cacheServiceV2.getCafeteriaList(campusId);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(resultList);
    }
}
