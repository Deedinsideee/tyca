package ru.alexandr.services.tyca.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alexandr.services.tyca.dtos.FilterDTO;
import ru.alexandr.services.tyca.entities.Venue;
import ru.alexandr.services.tyca.repository.impl.VenueRepositoryCustom;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SatanaService {

    private final VenueRepositoryCustom repository;

    public List<Venue> getVenues (FilterDTO filterDTO){

        List<Venue> venues = repository.findVenuesByFilters(filterDTO);
        return repository.findVenuesByFilters(filterDTO);
    }

}
