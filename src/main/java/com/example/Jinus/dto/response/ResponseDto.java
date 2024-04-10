package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDto {
    private String version;
    private TemplateDTO template;

    public ResponseDto(String version, TemplateDTO template) {
        this.version = version;
        this.template = template;
    }

    @Getter
    @Setter
    public static class TemplateDTO {
        private List<ComponentDto> outputs;

        public TemplateDTO(List<ComponentDto> outputs) {
            this.outputs = outputs;
        }
    }
}
