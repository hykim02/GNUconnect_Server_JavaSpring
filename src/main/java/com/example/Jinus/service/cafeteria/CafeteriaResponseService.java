package com.example.Jinus.service.cafeteria;

import com.example.Jinus.dto.data.CafeteriaDto;
import com.example.Jinus.dto.response.ButtonDto;
import com.example.Jinus.dto.response.ListItemDto;
import com.example.Jinus.dto.response.ResponseDto;
import com.example.Jinus.utility.JsonUtils;
import com.example.Jinus.utility.ListCardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CafeteriaResponseService {

    // 식당 리스트 카드 생성
    public String createCafeteriaListCard(String campusName, List<CafeteriaDto> cafeteriaList) {
        List<ListItemDto> listItems = mappingCafeteriaList(campusName, cafeteriaList);
        ResponseDto responseDto = ListCardResponse.mappingResponseDto(
                "어떤 교내 식당 정보가 알고 싶어?", listItems, mappingButtonDto()
        );
        return JsonUtils.toJsonResponse(responseDto);
    }

    // 식당 아이템 객체 생성
    private List<ListItemDto> mappingCafeteriaList(String campusName, List<CafeteriaDto> cafeteriaList) {
        List<ListItemDto> listItems = new ArrayList<>();
        for (CafeteriaDto cafeteria : cafeteriaList) {
            String userMessage = campusName + " " + cafeteria.getCafeteriaNameKo();
            ListItemDto listItem = new ListItemDto(
                    cafeteria.getCafeteriaNameKo(), campusName,
                    cafeteria.getThumbnailUrl(), "message", userMessage
            );
            listItems.add(listItem);
        }
        return listItems;
    }

    // 더보기 버튼 생성
    private List<ButtonDto> mappingButtonDto() {
        List<ButtonDto> buttonDto = new ArrayList<>();
        Map<String, Object> extra = new HashMap<>();
        extra.put("sys_campus_id", -1);
        buttonDto.add(new ButtonDto("더보기", "block", "66067167cdd882158c759fc2", extra));
        return buttonDto;
    }
}
