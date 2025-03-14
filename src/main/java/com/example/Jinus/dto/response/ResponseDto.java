package com.example.Jinus.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

public record ResponseDto(String version, TemplateDto template) {

}
