package com.example.Jinus.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NoticeResponseDto {
    private String version;
    private TemplateDTO template;

    public NoticeResponseDto(String version, TemplateDTO template) {
        this.version = version;
        this.template = template;
    }

    @Getter
    @Setter
    public static class TemplateDTO {
        private List<CarouselItemDTO> outputs;

        public TemplateDTO(List<CarouselItemDTO> outputs) {
            this.outputs = outputs;
        }

        @Getter
        @Setter
        public static class CarouselItemDTO {
            private CarouselDTO carousel;

            public CarouselItemDTO(CarouselDTO carousel) {
                this.carousel = carousel;
            }

            @Getter
            @Setter
            public static class CarouselDTO {
                private String type;
                private List<CardItemDTO> items;

                public CarouselDTO(String type, List<CardItemDTO> items) {
                    this.type = type;
                    this.items = items;
                }

                @Getter
                @Setter
                public static class CardItemDTO {
                    private HeaderDTO header;
                    private List<MenuItemDTO> items;
                    private List<ButtonDTO> buttons;

                    public CardItemDTO(HeaderDTO header, List<MenuItemDTO> items, List<ButtonDTO> buttons) {
                        this.header = header;
                        this.items = items;
                        this.buttons = buttons;
                    }

                    @Getter
                    @Setter
                    public static class HeaderDTO {
                        private String title;

                        public HeaderDTO(String title) {
                            this.title = title;
                        }
                    }

                    @Getter
                    @Setter
                    public static class MenuItemDTO {
                        private String title;
                        private String description;

                        public MenuItemDTO(String title, String description) {
                            this.title = title;
                            this.description = description;
                        }
                    }

                    @Getter
                    @Setter
                    public static class ButtonDTO {
                        private String label;
                        private String action;
                        private String messageText;

                        public ButtonDTO(String label, String action, String messageText) {
                            this.label = label;
                            this.action = action;
                            this.messageText = messageText;
                        }
                    }
                }
            }
        }
    }
}













