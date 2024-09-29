package ru.alexandr.services.tyca.dtos;

import lombok.Getter;

import java.util.List;
@Getter
public class ChipFilterDTO {
    private String type;    // Тип фильтра ("chip")
    private String header;  // Заголовок фильтра
    private String size;    // Размер чипа ("big", "small")
    private List<ChipItemDTO> chipList;  // Список чипов (максимум 2 элемента)
}