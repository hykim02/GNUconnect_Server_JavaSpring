package com.example.Jinus.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class ActionDto {
    private String name;
    private Object clientExtra;
    private ActionParamsDto params;
    private String id;
    private Map<String, DetailParamDto> detailParams;
}
