package com.example.Jinus.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.inject.Any;
import java.util.Map;

@Getter
@Setter
public class ActionDto {
    private String id; // skill id
    private String name; // skill name
    private Map<String, String> params;
    private Map<String, Object> clientExtra;
    private Map<String, DetailParamDto> detailParams;
}
