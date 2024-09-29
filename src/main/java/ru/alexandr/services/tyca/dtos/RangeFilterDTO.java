package ru.alexandr.services.tyca.dtos;

import lombok.Getter;

import java.util.List;
@Getter
public class RangeFilterDTO {
    private String type;      // Тип фильтра ("range" или "rangebar")
    private String header;    // Заголовок фильтра
    private Integer min;      // Минимальное значение (например, 16)
    private Integer max;      // Максимальное значение (например, 60)
    private List<Integer> options; // Опции (максимум 2 элемента)
    private String unit;      // Единица измерения (например, "лет", "₽")
}