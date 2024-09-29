package ru.alexandr.services.tyca.dtos;

import lombok.Getter;

@Getter
public class ChipItemDTO {
    private String text;    // Текст на чипе (например, "Клубы", "Бары")
    private String icon;    // Иконка (например, "clubIcon", "barIcon")
    private Boolean active; // Активен ли чип
}