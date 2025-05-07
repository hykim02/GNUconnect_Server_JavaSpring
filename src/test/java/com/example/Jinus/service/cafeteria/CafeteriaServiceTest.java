package com.example.Jinus.service.cafeteria;

import com.example.Jinus.dto.data.CafeteriaDto;
import com.example.Jinus.repository.cafeteria.CafeteriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CafeteriaServiceTest {

    @InjectMocks
    private CacheService cacheServiceV2;
    @Mock
    private CafeteriaRepository cafeteriaRepositoryV2;

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
