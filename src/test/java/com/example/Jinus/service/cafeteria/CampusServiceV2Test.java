package com.example.Jinus.service.cafeteria;

import com.example.Jinus.entity.cafeteria.CampusEntity;
import com.example.Jinus.repository.v2.cafeteria.CampusRepositoryV2;
import com.example.Jinus.service.v2.cafeteria.CampusServiceV2;
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

@ExtendWith(MockitoExtension.class)
public class CampusServiceV2Test {

    @InjectMocks
    private CampusServiceV2 campusServiceV2;
    @Mock
    private CampusRepositoryV2 campusRepositoryV2;

    @Test
    @DisplayName("사용자 캠퍼스 찾기")
    public void checkUserCampusName() {
        // given - 파라미터
        int campusId = 1;
        String campusName = "가좌캠퍼스";

        // when
        Mockito.when(campusRepositoryV2.findCampusNameByCampusId(campusId)).thenReturn(campusName); // Mocking
        String result = campusServiceV2.getUserCampusName(campusId);

        // then
        assertThat(result).isEqualTo(campusName);
    }

    @Test
    @DisplayName("id < 5인 캠퍼스 리스트 찾기")
    public void checkCampusList_campusIdLessThan5() {
        // given - 예상 데이터
        List<CampusEntity> campusList = new ArrayList<>();
        campusList.add(new CampusEntity(1, "가좌", "url1"));
        campusList.add(new CampusEntity(2, "칠암", "url2"));

        // when
        Mockito.when(campusRepositoryV2.findCampusList()).thenReturn(campusList);
        List<CampusEntity> result = campusServiceV2.getCampusList();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(campusList);
    }
}
