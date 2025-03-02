package com.example.Jinus.service.v2.notice;

import com.example.Jinus.dto.response.ButtonDto;
import com.example.Jinus.dto.response.LinkItemDto;
import com.example.Jinus.dto.response.ListCardDto;
import com.example.Jinus.dto.response.ListItemDto;
import com.example.Jinus.entity.notice.NoticeEntity;
import com.example.Jinus.repository.v2.notice.NoticeRepositoryV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

@Service
public class NoticeServiceV2 {

    private final NoticeRepositoryV2 noticeRepositoryV2;

    public NoticeServiceV2(NoticeRepositoryV2 noticeRepositoryV2){
        this.noticeRepositoryV2 = noticeRepositoryV2;
    }

    // categoryId에 해당하는 공지 찾아 itemList 객체 생성
    public List<ListItemDto> getNoticeList(int categoryId, String mi, String bbsId, String departmentEng) {
        List<NoticeEntity> noticeEntities = noticeRepositoryV2.findNoticeListByCategoryId(categoryId);
        return makeNoticeItemList(noticeEntities, mi, bbsId, departmentEng);
    }

    // 공지 아이템 리스트 dto 매핑
    public List<ListItemDto> makeNoticeItemList(List<NoticeEntity> noticeEntities,
                                   String mi, String bbsId, String departmentEng) {
        List<ListItemDto> noticeItems = new ArrayList<>();

        for (NoticeEntity noticeEntity : noticeEntities) {
            // 공지 아이템 링크 객체 생성
            LinkItemDto link = new LinkItemDto(noticeDetailUrl(departmentEng, mi, bbsId, noticeEntity.getNttSn()));
            // 리스트 아이템 객체 생성
            ListItemDto noticeItemDto = new ListItemDto(
                    noticeEntity.getTitle(), noticeEntity.getCreatedAt().substring(0, 10), link);
            noticeItems.add(noticeItemDto);
        }
        return noticeItems;
    }

    // 아이템 버튼 생성
    public List<ButtonDto> makeButton(String departmentEng, String mi, String bbsId) {
        List<ButtonDto> buttons = new ArrayList<>();
        ButtonDto button = new ButtonDto("더보기", "webLink", noticeCategoryUrl(departmentEng, mi, bbsId));
        buttons.add(button);
        return buttons;
    }

    // 공지 상세페이지 주소 생성
    public String noticeDetailUrl(String departmentEng, String mi, String bbsId, int nttSn) {
        String baseUrl = "https://www.gnu.ac.kr/" + departmentEng + "/na/ntt/selectNttInfo.do?";
        return baseUrl + "mi=" + mi + "&bbsId=" + bbsId + "&nttSn=" + nttSn;
    }

    // 공지 카테고리 주소
    public String noticeCategoryUrl(String departmentEng, String mi, String bbsId) {
        String baseUrl = "https://www.gnu.ac.kr/" + departmentEng + "/na/ntt/selectNttList.do?";
        return baseUrl + "mi=" + mi + "&bbsId=" + bbsId;
    }
}
