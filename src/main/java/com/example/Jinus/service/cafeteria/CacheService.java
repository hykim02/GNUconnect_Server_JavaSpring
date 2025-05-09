package com.example.Jinus.service.cafeteria;

import com.example.Jinus.config.RedisConfig;
import com.example.Jinus.dto.data.CafeteriaDto;
import com.example.Jinus.dto.data.DietDto;
import com.example.Jinus.dto.data.HandleRequestDto;
import com.example.Jinus.repository.cafeteria.CafeteriaRepository;
import com.example.Jinus.repository.cafeteria.DietRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheService {
    private final DietRepository dietRepositoryV2;
    private final CafeteriaRepository cafeteriaRepositoryV2;
    private final RedisConfig redisConfig;

    public List<DietDto> getDietList(HandleRequestDto parameters, int cafeteriaId) {
        String key = parameters.getDietDate() + "::" + parameters.getPeriod() + "::" + cafeteriaId;

        List<DietDto> cached = (List<DietDto>) redisConfig.redisTemplate().opsForValue().get(key);
        if (cached != null) return cached;

        List<DietDto> result = dietRepositoryV2.findDietList(parameters.getDietDate(), parameters.getPeriod(), cafeteriaId);

        if (result != null && !result.isEmpty()) {
            long ttlSeconds = calculateTtlUntilNextMidnight(parameters.getDietDate());
            redisConfig.redisTemplate().opsForValue().set(key, result, Duration.ofSeconds(ttlSeconds));
        }

        return result;
    }

    // 식단 데이터 TTL 동적 생성 -> 조회 시간과 하루 뒤의 자정시간까지의 시간차이가 TTL 시간
    // 즉, 날짜가 바뀌면 만료되어야 함
    private long calculateTtlUntilNextMidnight(Date dietDate) {
        LocalDateTime expireAt = dietDate.toLocalDate().plusDays(1).atStartOfDay(); // dietDate + 1일 자정
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(now, expireAt);
        long seconds = duration.getSeconds();

        // 만약 이미 만료되었거나, 현재 시간이 더 크면 0 리턴 (저장 안 함)
        return seconds > 0 ? seconds : 0;
    }


    // Redis에서 조회 (없으면 DB에서 가져옴)
//    @Cacheable(
//            value = "cafeteriaList",
//            key = "#p0",
//            cacheManager = "contentCacheManager")
    public List<CafeteriaDto> getCafeteriaList(int campusId) {
        return cafeteriaRepositoryV2.findCafeteriaListByCampusId(campusId);
    }
}
