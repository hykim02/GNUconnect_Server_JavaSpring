package com.example.Jinus.service.v2.notice;

import com.example.Jinus.dto.response.*;
import com.example.Jinus.entity.notice.NoticeCategoryEntity;
import com.example.Jinus.entity.notice.NoticeEntity;
import com.example.Jinus.repository.v2.notice.NoticeRepositoryV2;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.TextCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@Service
@RequiredArgsConstructor
public class NoticeServiceV2 {

    private final NoticeRepositoryV2 noticeRepositoryV2;
    private final CategoryServiceV2 categoryServiceV2;

    // db에 학과정보가 있는 경우 -> 공지리스트 반환
    public String existUserReturnNotice(String departmentEng, int departmentId) {
        // 학과 공지 카테고리들 가져오기
        List<NoticeCategoryEntity> categoryEntities = categoryServiceV2.getCategoryEntity(departmentId);

        // 카테고리 존재 여부 확인
        if (!categoryEntities.isEmpty()) {
            return mappingCarouselItems(categoryEntities, departmentEng);
        } else {
            return thereIsNoCategory();
        }
    }

    // 사용자 학과 정보가 없는 경우 학과인증 블록 리턴 예외처리
    public String doesNotExistUserReturnBlock() {
        List<ButtonDto> buttonList = new ArrayList<>();
        // 블록 버튼 생성
        ButtonDto buttonDto = new ButtonDto("학과 등록", "block", null,"66cf0c8ae5715f75b254dfea");
        buttonList.add(buttonDto);
        return TextCardResponse.textCardResponse("학과 등록이 필요한 서비스야! 학과 등록을 진행해줘.", buttonList);
    }

    // 공지가 존재하는 경우
    public String mappingCarouselItems(List<NoticeCategoryEntity> categoryEntities, String departmentEng) {
        // 카테고리 리스트 생성 (캐로셀 아이템 리스트)
        List<CarouselItemDto> categoryList = new ArrayList<>();
        for (NoticeCategoryEntity entity : categoryEntities) {
            // 해당 카테고리의 공지 ListCard 생성
            List<ListItemDto> noticeItemList = getNoticeList(
                    entity.getId(), valueOf(entity.getMi()), valueOf(entity.getBbsId()), departmentEng);
            // ListCard 버튼 생성
            List<ButtonDto> buttonList = makeButton(
                    departmentEng, valueOf(entity.getMi()), valueOf(entity.getBbsId()));
            // 카테고리 제목 header 객체 생성
            HeaderDto title = new HeaderDto(entity.getCategory());
            // 하나의 카테고리 아이템 생성
            CarouselItemDto carouselItem = new CarouselItemDto(title, noticeItemList, buttonList);
            categoryList.add(carouselItem);
        }
        CarouselDto carousel = new CarouselDto("listCard", categoryList);
        List<ComponentDto> componentList = new ArrayList<>();
        componentList.add(new ComponentDto(carousel));
        TemplateDto templateDto = new TemplateDto(componentList, null);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);
        return JsonUtils.toJsonResponse(responseDto);
    }

    // 공지가 존재하지 않는 경우 예외처리
    public String thereIsNoCategory() {
        List<ButtonDto> buttonList = new ArrayList<>();
        ButtonDto buttonDto = new ButtonDto("게시판 등록 요청", "webLink", "https://forms.gle/cSMheFmmGDe7P3RD6");
        buttonList.add(buttonDto);
        return TextCardResponse.textCardResponse("최근에 등록된 공지사항이 없어!", buttonList);
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
