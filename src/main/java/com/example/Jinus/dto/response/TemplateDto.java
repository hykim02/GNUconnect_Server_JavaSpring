package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TemplateDto {
    private List<ComponentDto> outputs;
    private List<QuickReplyDto> quickReplies;

    public TemplateDto(List<ComponentDto> outputs) {
        this.outputs = outputs;
    }

    public TemplateDto(List<ComponentDto> outputs, List<QuickReplyDto> quickReplies) {
        this.outputs = outputs;
        this.quickReplies = quickReplies;
    }
}
