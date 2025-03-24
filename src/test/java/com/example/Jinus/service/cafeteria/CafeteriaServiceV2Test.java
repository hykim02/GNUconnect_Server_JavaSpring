package com.example.Jinus.service.cafeteria;

import com.example.Jinus.config.RedisCacheManager;
import com.example.Jinus.dto.data.CafeteriaDto;
import com.example.Jinus.repository.v2.cafeteria.CafeteriaRepositoryV2;
import com.example.Jinus.service.v2.cafeteria.CacheServiceV2;
import com.example.Jinus.service.v2.cafeteria.CafeteriaServiceV2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.ExpectedCount.times;

@ExtendWith(MockitoExtension.class)
public class CafeteriaServiceV2Test {

    @InjectMocks
    private CacheServiceV2 cacheServiceV2;
    @Mock
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
