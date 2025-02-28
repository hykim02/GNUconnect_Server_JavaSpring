package com.example.Jinus.service.v2.notice;

import com.example.Jinus.repository.v2.notice.NoticeRepositoryV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceV2 {

    private final NoticeRepositoryV2 noticeRepositoryV2;

    public NoticeServiceV2(NoticeRepositoryV2 noticeRepositoryV2){
        this.noticeRepositoryV2 = noticeRepositoryV2;
    }
}
