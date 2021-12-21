package rssapijava.controller;

import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Place;
import rssapijava.repository.CityRepository;
import rssapijava.repository.PlaceRepository;
import rssapijava.repository.ScheduleRepository;
import rssapijava.resource.CityResource;
import rssapijava.resource.PlaceResource;
import rssapijava.resource.ScheduleResource;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/place")
public class PlaceController
{
    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;
    private final ScheduleRepository scheduleRepository;

    public PlaceController(PlaceRepository placeRepository, CityRepository cityRepository, ScheduleRepository scheduleRepository)
    {
        this.placeRepository = placeRepository;
        this.cityRepository = cityRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    PlaceResource[] getAll(
            @RequestParam(required = false) Integer cityId,
            @RequestParam(required = false) Object expand)
    {
        Place[] entities = cityId == null
                ? placeRepository.select()
                : placeRepository.selectByCityId(cityId);
        return Arrays.stream(entities)
                .map(entity ->
                {
                    PlaceResource resource = new PlaceResource(entity);
                    if (expand != null)
                    {
                        resource.setCity(new CityResource(
                                cityRepository.select(entity.getCityId()))
                        );
                        resource.setSchedule(
                                Arrays.stream(scheduleRepository.selectByPlaceId(entity.getId()))
                                        .map(e -> new ScheduleResource(e))
                                        .toArray(ScheduleResource[]::new)
                        );
                    }
                    return resource;
                })
                .toArray(PlaceResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    PlaceResource get(@PathVariable Integer id,
                        @RequestParam(required = false) Object expand) {
        Place entity = placeRepository.select(id);
        if (entity == null) return null;
        PlaceResource resource = new PlaceResource(entity);
        if (expand != null)
            resource.setCity(
                    new CityResource(cityRepository.select(entity.getCityId()))
            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    PlaceResource post(@RequestBody PlaceResource resource) {
        Place entity = placeRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new PlaceResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    PlaceResource put(@PathVariable Integer id,
                        @RequestBody PlaceResource resource) {
        Place entity = placeRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new PlaceResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    PlaceResource delete(@PathVariable Integer id) {
        Place entity = placeRepository.delete(id);
        if (entity == null) return null;
        PlaceResource resource = new PlaceResource(entity);
        return resource;
    }
}
