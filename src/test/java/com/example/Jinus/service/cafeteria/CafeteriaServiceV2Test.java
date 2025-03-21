package com.example.Jinus.service.cafeteria;

import com.example.Jinus.config.RedisCacheManager;
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
@SpringBootTest
public class CafeteriaServiceV2Test {

    @Autowired
    private CafeteriaServiceV2 cafeteriaServiceV2;
    @Autowired
    private RedisTemplate redisTemplate;
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

//    @Test
//    @DisplayName("campusId, cafeteriaName을 키값으로 캐싱하여 cafeteriaId 값을 찾는다.")
//    public void checkCacheData() {
//        // given
//        int campusId = 1;
//        String cafeteriaName = "교직원식당";
//        int expectedCafeteriaId = 4;
//        String expectedCacheKey = "cafeteridId::" + campusId + "::" + cafeteriaName;  // 캐싱 키 예상값
//
//        // Mock 설정: DB 조회 시 특정 값 반환하도록 설정
//        Mockito.when(cafeteriaRepositoryV2.findCafeteriaId(cafeteriaName, campusId)).thenReturn(Optional.of(expectedCafeteriaId));
//
//        // when: 첫 번째 호출 (DB 조회 발생 후 Redis에 캐싱됨)
//        int cafeteriaId = cafeteriaServiceV2.getCafeteriaId(cafeteriaName, campusId);
//
//        // then: DB에서 가져온 값이 기대한 값과 같은지 확인
//        assertThat(cafeteriaId).isEqualTo(expectedCafeteriaId);
//
//        // Redis에서 직접 키 조회하여 확인
//        ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
//        String cachedValue = valueOps.get(expectedCacheKey);
//
//        assertThat(cachedValue).isNotNull(); // 캐시가 존재해야 함
//        assertThat(Integer.parseInt(cachedValue)).isEqualTo(expectedCafeteriaId); // 저장된 값이 예상한 값과 같은지 확인
//
//        // 캐시가 적용되었는지 검증: 두 번째 호출에서는 DB 조회가 발생하면 안 됨
//        int cachedCafeteriaId = cafeteriaServiceV2.getCafeteriaId(cafeteriaName, campusId);
//
//        // 두 번째 호출은 캐시에서 가져와야 하므로 DB 호출이 한 번만 발생해야 함
//        verify(cafeteriaRepositoryV2, Mockito.times(1)).findCafeteriaId(cafeteriaName, campusId);
//
//        // 캐시된 값과 두 번째 조회한 값이 같은지 확인
//        assertThat(cachedCafeteriaId).isEqualTo(expectedCafeteriaId);
//
//    }
}
