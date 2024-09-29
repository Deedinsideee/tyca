package ru.alexandr.services.tyca.dtos;

import lombok.Getter;

@Getter
public class TimeRangeFilterDTO {
    private String type;    // Тип фильтра ("rangeTime")
    private String header;  // Заголовок фильтра
    private String min;     // Начальное время (например, "23:00")
    private String max;     // Конечное время (например, "05:00")
}