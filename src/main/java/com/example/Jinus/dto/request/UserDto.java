package com.example.Jinus.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String id;
    private String type;
    private PropertiesDto properties;
}
