package com.example.Jinus.service.v2.cafeteria;

import com.example.Jinus.dto.data.HandleRequestDto;
import com.example.Jinus.dto.response.*;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.SimpleTextResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietResponseServiceV2 {

    // 응답 객체 매핑
    public String mappingResponse(HandleRequestDto parameters, String imgUrl, String title, String description) {
        // imgUrl 객체 생성
        ThumbnailDto thumbnail = new ThumbnailDto(imgUrl);

        // 버튼 리스트 객체 생성
        List<ButtonDto> buttonList = List.of(new ButtonDto("공유하기", "share"));

        // 아이템 객체 생성
        BasicCardDto basicCardDto = new BasicCardDto(title, description, thumbnail, buttonList);
        List<ComponentDto> outputs = List.of(new ComponentDto(basicCardDto));

        List<QuickReplyDto> quickReplies = mappingQuickReply(parameters);

        TemplateDto templateDto = new TemplateDto(outputs, quickReplies);
        ResponseDto responseDto = new ResponseDto("2.0", templateDto);

        return JsonUtils.toJsonResponse(responseDto);
    }

    private List<QuickReplyDto> mappingQuickReply(HandleRequestDto parameters) {
        List<String> periods = getNextMealPeriods(parameters.getPeriod());
        return periods.stream()
                .map(period -> createQuickReply(period, parameters))
                .collect(Collectors.toList());
    }

    // 현재 시간대(period)에 따라 다음 선택할 식사 시간대를 반환
    private List<String> getNextMealPeriods(String currentPeriod) {
        return switch (currentPeriod) {
            case "아침" -> List.of("점심", "저녁");
            case "점심" -> List.of("아침", "저녁");
            default -> List.of("아침", "점심");
        };
    }

    // QuickReplyDto 객체를 생성하는 메서드
    private QuickReplyDto createQuickReply(String period, HandleRequestDto parameters) {
        String message = String.format("%s %s %s %s 메뉴",
                parameters.getCampusName(),
                parameters.getCafeteriaName(),
                parameters.getDay(),
                period);
        return new QuickReplyDto(period, "message", message);
    }

    // 캠퍼스에 식당이 존재하지 않는 경우 메시지 출력
    public String errorMsgThereIsNoCafeteria() {
        return SimpleTextResponse
                .simpleTextResponse("식당을 찾지 못했어!\n어떤 캠퍼스에 있는 식당인지 정확히 알려줘!");
    }
}
