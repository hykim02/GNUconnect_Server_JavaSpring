package com.example.Jinus.service.v2.notice;

import com.example.Jinus.dto.response.LinkItemDto;
import com.example.Jinus.dto.response.ListItemDto;
import com.example.Jinus.entity.notice.NoticeEntity;
import com.example.Jinus.repository.v2.notice.NoticeRepositoryV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoticeServiceV2 {

    private final NoticeRepositoryV2 noticeRepositoryV2;

    public NoticeServiceV2(NoticeRepositoryV2 noticeRepositoryV2){
        this.noticeRepositoryV2 = noticeRepositoryV2;
    }

    // categoryId에 해당하는 공지 찾기
    public void getNoticeList(int categoryId) {
        List<NoticeEntity> noticeEntities = noticeRepositoryV2.findNoticeListByCategoryId(categoryId);
    }

    public List<Map<String, Object>> noticeListToMap(List<NoticeEntity> noticeEntities) {
        List<Map<String, Object>> noticeMapList = new ArrayList<>();

        for (NoticeEntity noticeEntity : noticeEntities) {
            Map<String, Object> noticeHashMap = new HashMap<>();
            noticeHashMap.put("title", noticeEntity.getTitle());
            noticeHashMap.put("nttSn", noticeEntity.getNttSn());
            noticeHashMap.put("createdAt", noticeEntity.getCreatedAt());
            noticeMapList.add(noticeHashMap);
        }
        return noticeMapList;
    }

    // 공지 아이템 리스트 dto 매핑
//    public void makeNoticeItemList(List<NoticeEntity> noticeEntities) {
//        for (NoticeEntity noticeEntity : noticeEntities) {
//            // 공지 아이템 링크 객체 생성
//            LinkItemDto link = new LinkItemDto()
//            ListItemDto noticeListItemDto = new ListItemDto(
//                    noticeEntity.getTitle(),
//                    noticeEntity.getCreatedAt().substring(0, 10),
//                    );
//        }
//    }

    // 공지 상세페이지 주소 생성
    public String noticeDetailUrl(String departmentEng, String mi, String bbsId, String nttSn) {
        String baseUrl = "https://www.gnu.ac.kr/" + departmentEng + "/na/ntt/selectNttInfo.do?";
        return baseUrl + "mi=" + mi + "&bbsId=" + bbsId + "&nttSn=" + nttSn;
    }
}
