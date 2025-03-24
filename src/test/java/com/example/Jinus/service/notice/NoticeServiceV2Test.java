package com.example.Jinus.service.notice;

import com.example.Jinus.dto.response.ListItemDto;
import com.example.Jinus.entity.notice.NoticeCategoryEntity;
import com.example.Jinus.entity.notice.NoticeEntity;
import com.example.Jinus.repository.v2.notice.NoticeRepositoryV2;
import com.example.Jinus.service.v2.notice.CategoryServiceV2;
import com.example.Jinus.service.v2.notice.NoticeServiceV2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoticeServiceV2Test {

    @InjectMocks
    private NoticeServiceV2 noticeServiceV2;
    @Mock
    private CategoryServiceV2 categoryServiceV2;
    @Mock
    private NoticeRepositoryV2 noticeRepositoryV2;

    private List<NoticeCategoryEntity> mockCategoryEntities;
    private List<NoticeEntity> mockNoticeEntities;

    @BeforeEach
    void setUp() {
        // 공지 카테고리 엔티티 Mock 데이터 생성
        mockCategoryEntities = new ArrayList<>();
        NoticeCategoryEntity category = new NoticeCategoryEntity();
        category.setId(1);
        category.setMi(123);
        category.setBbsId(456);
        category.setCategory("학과공지");
        mockCategoryEntities.add(category);

        // 공지 엔티티 Mock 데이터 생성
        mockNoticeEntities = new ArrayList<>();
        NoticeEntity notice = new NoticeEntity();
        notice.setTitle("시험 일정 공지");
        notice.setCreatedAt("2025-03-24");
        notice.setNttSn(1001);
        mockNoticeEntities.add(notice);
    }

    @Test
    @DisplayName("학과정보 존재하는 경우 공지리스트 반환")
    void checkExistUserReturnNotice() {
        // given
        String departmentEng = "computer";
        int departmentId = 1;

        when(categoryServiceV2.getCategoryEntity(departmentId)).thenReturn(mockCategoryEntities);
        when(noticeRepositoryV2.findNoticeListByCategoryId(1)).thenReturn(mockNoticeEntities);

        // when
        String result = noticeServiceV2.existUserReturnNotice(departmentEng, departmentId);

        // then
        assertNotNull(result);
        assertTrue(result.contains("학과공지")); // JSON 결과에 "학과공지" 포함 확인
        verify(categoryServiceV2, times(1)).getCategoryEntity(departmentId);
    }

    @Test
    @DisplayName("공지가 존재하지 않는 경우")
    void checkThereIsNoNoticeData() {
        // given
        String departmentEng = "computer";
        int departmentId = 1;

        when(categoryServiceV2.getCategoryEntity(departmentId)).thenReturn(new ArrayList<>());

        // when
        String result = noticeServiceV2.existUserReturnNotice(departmentEng, departmentId);

        // then
        assertNotNull(result);
        assertTrue(result.contains("최근에 등록된 공지사항이 없어!"));
    }

    @Test
    @DisplayName("학과 정보가 없어서 학과 등록 블록 반환")
    void checkDoesNotExistUserReturnBlock() {
        // when
        String result = noticeServiceV2.doesNotExistUserReturnBlock();

        // then
        assertNotNull(result);
        assertTrue(result.contains("학과 등록"));
        assertTrue(result.contains("66cf0c8ae5715f75b254dfea")); // 블록 ID 포함 여부 확인
    }

    @Test
    @DisplayName("공지 가져오기")
    void checkGetNoticeList() {
        // given
        int categoryId = 1;
        String departmentEng = "computer";
        String mi = "123";
        String bbsId = "456";

        when(noticeRepositoryV2.findNoticeListByCategoryId(categoryId)).thenReturn(mockNoticeEntities);

        // when
        List<ListItemDto> result = noticeServiceV2.getNoticeList(categoryId, mi, bbsId, departmentEng);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("시험 일정 공지", result.getFirst().getTitle());
    }

    @Test
    @DisplayName("url 생성 테스트")
    void checkNoticeDetailUrl() {
        // given
        String departmentEng = "computer";
        String mi = "123";
        String bbsId = "456";
        int nttSn = 1001;

        // when
        String url = noticeServiceV2.noticeDetailUrl(departmentEng, mi, bbsId, nttSn);

        // then
        assertEquals("https://www.gnu.ac.kr/computer/na/ntt/selectNttInfo.do?mi=123&bbsId=456&nttSn=1001", url);
    }
}
