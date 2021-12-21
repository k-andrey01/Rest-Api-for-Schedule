package rssapijava.controller;

import org.springframework.web.bind.annotation.*;
import rssapijava.entity.City;
import rssapijava.repository.CityRepository;
import rssapijava.repository.PlaceRepository;
import rssapijava.resource.CityResource;
import rssapijava.resource.PlaceResource;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/city")
public class CityController {
    private final CityRepository cityRepository;
    private final PlaceRepository placeRepository;

    public CityController(CityRepository cityRepository, PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        this.cityRepository = cityRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    CityResource[] getAll(@RequestParam(required = false) Object expand) {
        return Arrays.stream(cityRepository.select())
                .map(entity -> {
                    CityResource resource = new CityResource(entity);
                    if (expand != null)
                        resource.setPlace(
                                Arrays.stream(placeRepository.selectByCityId(entity.getId()))
                                        .map(e -> new PlaceResource(e))
                                        .toArray(PlaceResource[]::new)
                        );
                    return resource;
                })
                .toArray(CityResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    CityResource get(@PathVariable Integer id,
                          @RequestParam(required = false) Object expand) {
        City entity = cityRepository.select(id);
        if (entity == null) return null;
        CityResource resource = new CityResource(entity);
        if (expand != null)
            resource.setPlace(
                    Arrays.stream(placeRepository.selectByCityId(entity.getId()))
                            .map(e -> new PlaceResource(e))
                            .toArray(PlaceResource[]::new)
            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    CityResource post(@RequestBody CityResource resource) {
        City entity = cityRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new CityResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    CityResource put(@PathVariable Integer id,
                          @RequestBody CityResource resource) {
        City entity = cityRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new CityResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    CityResource delete(@PathVariable Integer id) {
        City entity = cityRepository.delete(id);
        if (entity == null) return null;
        return new CityResource(entity);
    }
}
