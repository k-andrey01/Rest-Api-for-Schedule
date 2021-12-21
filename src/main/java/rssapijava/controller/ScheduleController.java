package rssapijava.controller;


import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Performance;
import rssapijava.entity.Schedule;
import rssapijava.repository.PerformanceRepository;
import rssapijava.repository.PlaceRepository;
import rssapijava.repository.ScheduleRepository;
import rssapijava.resource.PerformanceResource;
import rssapijava.resource.PlaceResource;
import rssapijava.resource.ScheduleResource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;



@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController
{
    private final ScheduleRepository scheduleRepository;
    private final PlaceRepository placeRepository;
    private final PerformanceRepository performanceRepository;

    public ScheduleController(ScheduleRepository scheduleRepository, PlaceRepository placeRepository, PerformanceRepository performanceRepository)
    {
        this.scheduleRepository = scheduleRepository;
        this.placeRepository = placeRepository;
        this.performanceRepository = performanceRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    ScheduleResource[] getAll(
            @RequestParam(required = false) Integer placeId,
            @RequestParam(required = false) Integer performanceId,
            @RequestParam(required = false) Object expand)
    {
        Set<Schedule> entities = null;
        if((placeId == null) && (performanceId == null))
        {
            entities = Set.of(scheduleRepository.select());
        }
        else if((placeId == null) && (performanceId != null))
        {
            entities = Set.of(scheduleRepository.selectByPerformanceId(performanceId));
        }
        else if((placeId != null) && (performanceId == null))
        {
            entities = Set.of(scheduleRepository.selectByPlaceId(placeId));
        }
        else if((placeId != null) && (performanceId != null))
        {
            Set<Schedule> entitiesByPlace = Set.of(scheduleRepository.selectByPlaceId(placeId));
            Set<Schedule> entitiesByPerformance = Set.of(scheduleRepository.selectByPerformanceId(performanceId));
            entities = intersection(entitiesByPlace, entitiesByPerformance);
        }
        return entities.stream()
                .map(entity -> {
                    ScheduleResource resource = new ScheduleResource(entity);
                    if (expand != null)
                    {
                        resource.setPlace(
                                new PlaceResource(placeRepository.select(entity.getPlaceId()))
                        );
                        resource.setPerformance(
                                new PerformanceResource(performanceRepository.select(entity.getPerformanceId()))
                        );
                    }
                    return resource;
                })
                .toArray(ScheduleResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ScheduleResource get(
            @PathVariable Integer id,
            @RequestParam(required = false) Object expand) {
        Schedule entity = scheduleRepository.select(id);
        if (entity == null) return null;
        ScheduleResource resource = new ScheduleResource(entity);
        if (expand != null)
        {
            resource.setPlace(
                    new PlaceResource(placeRepository.select(entity.getPlaceId()))
            );
            resource.setPerformance(
                    new PerformanceResource(performanceRepository.select(entity.getPerformanceId()))
            );
        }
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    ScheduleResource post(@RequestBody ScheduleResource resource) {
        Schedule entity = scheduleRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new ScheduleResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ScheduleResource put(@PathVariable Integer id,
                            @RequestBody ScheduleResource resource) {
        Schedule entity = scheduleRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new ScheduleResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ScheduleResource delete(@PathVariable Integer id) {
        Schedule entity = scheduleRepository.delete(id);
        if (entity == null) return null;
        ScheduleResource resource = new ScheduleResource(entity);
        return resource;
    }

    private Set<Schedule> intersection(Set<Schedule> A, Set<Schedule> B)
    {
        ArrayList<Schedule> preanswer = new ArrayList<>();
        for(Schedule a : A)
        {
            for(Schedule b : B)
            {
                if (a.getId()==(b.getId()))
                {
                    preanswer.add(b);
                }
            }
        }
        return new HashSet<Schedule>(preanswer);
    }
}