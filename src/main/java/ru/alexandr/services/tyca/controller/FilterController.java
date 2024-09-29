package ru.alexandr.services.tyca.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.alexandr.services.tyca.dtos.FilterDTO;
import ru.alexandr.services.tyca.entities.Venue;
import ru.alexandr.services.tyca.service.SatanaService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilterController {

    private final SatanaService service;

    @PostMapping("/jara")
    public List<Venue> getVenues (@RequestBody FilterDTO filterDTO){
        return service.getVenues(filterDTO);
    }
    


}
