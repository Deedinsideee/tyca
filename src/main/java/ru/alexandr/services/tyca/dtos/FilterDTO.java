package ru.alexandr.services.tyca.dtos;

import lombok.Getter;

@Getter
public class FilterDTO {
    private ChipFilterDTO venueType;           // Фильтр по типу заведения
    private ChipFilterDTO dayOfWeek;           // Фильтр по дням недели
    private RangeFilterDTO audienceRange;      // Возрастной диапазон (например, 16-60 лет)
    private RangeFilterDTO priceRange;         // Ценовой диапазон (например, 500-10000 ₽)
    private TimeRangeFilterDTO operatingHours; // Время работы заведения
}