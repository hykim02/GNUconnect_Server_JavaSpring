package com.example.Jinus.repository.notice;

import com.example.Jinus.entity.notice.NoticeEntity;
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
public class NoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepositoryV2;

    @BeforeEach
    void setUp() {
        noticeRepositoryV2.save(new NoticeEntity(1, 10, "게시물1", 12, "3/4"));
        noticeRepositoryV2.save(new NoticeEntity(2, 10, "게시물2", 12, "3/4"));
        noticeRepositoryV2.save(new NoticeEntity(3, 15, "게시물1", 12, "3/4"));
    }

    @Test
    @DisplayName("카테고리id에 해당하는 공지 찾기")
    public void checkNoticeListById() {
        // given
        int categoryId = 10;
        // 예상 데이터
        List<NoticeEntity> noticeList = new ArrayList<>();
        noticeList.add(new NoticeEntity(1, 10, "게시물1", 12, "3/4"));
        noticeList.add(new NoticeEntity(2, 10, "게시물2", 12, "3/4"));

        // when
        List<NoticeEntity> result = noticeRepositoryV2.findNoticeListByCategoryId(categoryId);

        // then
        assertThat(result).hasSize(2);
        assertThat(result).usingRecursiveComparison().isEqualTo(noticeList);
    }
}
