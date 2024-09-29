package ru.alexandr.services.tyca.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.alexandr.services.tyca.dtos.ChipItemDTO;
import ru.alexandr.services.tyca.dtos.FilterDTO;
import ru.alexandr.services.tyca.entities.Filter;
import ru.alexandr.services.tyca.entities.Venue;
import ru.alexandr.services.tyca.entities.VenueFilter;
import ru.alexandr.services.tyca.entities.VenueType;


import java.util.List;
import java.util.stream.Collectors;

@Repository
public class VenueRepositoryCustomImpl implements VenueRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Venue> findVenuesByFilters(FilterDTO filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Venue> query = cb.createQuery(Venue.class);
        Root<Venue> venueRoot = query.from(Venue.class);

        // Join таблиц: venue -> venue_filter -> filter
        Join<Venue, VenueFilter> venueFilterJoin = venueRoot.join("venueFilters", JoinType.INNER);
        Join<VenueFilter, Filter> filterJoin = venueFilterJoin.join("filter", JoinType.INNER);
        Join<Venue, VenueType> venyeTypeJoin = venueRoot.join("venueType", JoinType.INNER);

        // Основной предикат для всех условий
        Predicate predicate = cb.conjunction();

        // 1. Фильтрация по типам заведений (например, "Караоке", "Бары")
        if (filter.getVenueType() != null && filter.getVenueType().getChipList() != null) {
            List<String> activeVenueTypes = filter.getVenueType().getChipList().stream()
                    .filter(ChipItemDTO::getActive)
                    .map(ChipItemDTO::getText)
                    .collect(Collectors.toList());

            if (!activeVenueTypes.isEmpty()) {
                predicate = cb.and(predicate, venyeTypeJoin.get("name").in(activeVenueTypes));
            }
        }

        /*// 2. Фильтрация по дню недели
        if (filter.getDayOfWeek() != null && filter.getDayOfWeek().getChipList() != null) {
            List<String> activeDays = filter.getDayOfWeek().getChipList().stream()
                    .filter(ChipItemDTO::getActive)
                    .map(ChipItemDTO::getText)
                    .collect(Collectors.toList());

            if (!activeDays.isEmpty()) {
                predicate = cb.and(predicate, filterJoin.get("filter").in(activeDays));
            }
        }*/

        // 3. Фильтрация по возрасту аудитории
        // 1. Фильтрация по возрастной аудитории
        if (filter.getAudienceRange() != null) {
            predicate = cb.and(predicate,
                    cb.and(
                            cb.greaterThanOrEqualTo(venueFilterJoin.get("intDataMin"), filter.getAudienceRange().getMin()),
                            cb.lessThanOrEqualTo(venueFilterJoin.get("intDataMax"), filter.getAudienceRange().getMax())
                    )
            );
        }

// 2. Фильтрация по ценовому диапазону
        if (filter.getPriceRange() != null) {
            predicate = cb.and(predicate,
                    cb.and(
                            cb.greaterThanOrEqualTo(venueFilterJoin.get("intDataMin"), filter.getPriceRange().getMin()),
                            cb.lessThanOrEqualTo(venueFilterJoin.get("intDataMax"), filter.getPriceRange().getMax())
                    )
            );
        }

// 3. Фильтрация по времени работы заведения
        if (filter.getOperatingHours() != null) {
            Integer minTime = convertTimeToMinutes(filter.getOperatingHours().getMin());
            Integer maxTime = convertTimeToMinutes(filter.getOperatingHours().getMax());

            // Здесь предполагается, что у вас есть поле для хранения времени в формате минут
            predicate = cb.and(predicate,
                    cb.and(
                            cb.greaterThanOrEqualTo(venueFilterJoin.get("intDataMin").as(Integer.class), minTime),
                            cb.lessThanOrEqualTo(venueFilterJoin.get("intDataMax").as(Integer.class), maxTime)
                    )
            );
        }

        // Применение предикатов к запросу
        query.select(venueRoot).distinct(true).where(predicate);

        // Выполнение запроса
        return entityManager.createQuery(query).getResultList();
    }

    // Метод для преобразования времени в минуты
    private Integer convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
}
