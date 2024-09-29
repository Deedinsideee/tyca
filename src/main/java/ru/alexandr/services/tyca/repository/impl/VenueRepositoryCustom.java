package ru.alexandr.services.tyca.repository.impl;


import ru.alexandr.services.tyca.dtos.FilterDTO;
import ru.alexandr.services.tyca.entities.Venue;

import java.util.List;

public interface VenueRepositoryCustom  {
    List<Venue> findVenuesByFilters(FilterDTO filters);


}
