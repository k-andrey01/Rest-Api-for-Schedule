package rssapijava.controller;


import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Performance;
import rssapijava.repository.LanguageRepository;
import rssapijava.repository.PerformanceRepository;
import rssapijava.repository.ScheduleRepository;
import rssapijava.repository.TypeRepository;
import rssapijava.resource.LanguageResource;
import rssapijava.resource.PerformanceResource;
import rssapijava.resource.ScheduleResource;
import rssapijava.resource.TypeResource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/performance")
public class PerformanceController
{
    private final PerformanceRepository performanceRepository;
    private final TypeRepository typeRepository;
    private final LanguageRepository languageRepository;
    private final ScheduleRepository scheduleRepository;

    public PerformanceController(PerformanceRepository performanceRepository, TypeRepository typeRepository, LanguageRepository languageRepository, ScheduleRepository scheduleRepository)
    {
        this.performanceRepository = performanceRepository;
        this.typeRepository = typeRepository;
        this.languageRepository = languageRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    PerformanceResource[] getAll(
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false) Integer languageId,
            @RequestParam(required = false) Object expand)
    {
        Set<Performance> entities = null;
        if((typeId == null) && (languageId == null))
        {
            entities = Set.of(performanceRepository.select());
        }
        else if((typeId != null) && (languageId == null))
        {
            entities = Set.of(performanceRepository.selectByTypeId(typeId));
        }
        else if((typeId == null) && (languageId != null))
        {
            entities = Set.of(performanceRepository.selectByLanguageId(languageId));
        }
        else if((typeId != null) && (languageId != null))
        {
            Set<Performance> entitiesByType = Set.of(performanceRepository.selectByTypeId(typeId));
            Set<Performance> entitiesByLanguage = Set.of(performanceRepository.selectByLanguageId(languageId));
            entities = intersection(entitiesByType, entitiesByLanguage);
        }
        return entities.stream()
                .map(entity -> {
                    PerformanceResource resource = new PerformanceResource(entity);
                    if (expand != null)
                    {
                        resource.setType(
                                new TypeResource(typeRepository.select(entity.getTypeId()))
                        );
                        resource.setLanguage(
                                new LanguageResource(languageRepository.select(entity.getLanguageId()))
                        );
                        resource.setSchedule(
                                Arrays.stream(scheduleRepository.selectByPerformanceId(entity.getId()))
                                        .map(e -> new ScheduleResource(e))
                                        .toArray(ScheduleResource[]::new)
                        );
                    }
                    return resource;
                })
                .toArray(PerformanceResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    PerformanceResource get(
            @PathVariable Integer id,
            @RequestParam(required = false) Object expand) {
        Performance entity = performanceRepository.select(id);
        if (entity == null) return null;
        PerformanceResource resource = new PerformanceResource(entity);
        if (expand != null)
        {
            resource.setType(
                    new TypeResource(typeRepository.select(entity.getTypeId()))
            );
            resource.setLanguage(
                    new LanguageResource(languageRepository.select(entity.getLanguageId()))
            );
        }
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    PerformanceResource post(@RequestBody PerformanceResource resource) {
        Performance entity = performanceRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new PerformanceResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    PerformanceResource put(@PathVariable Integer id,
                      @RequestBody PerformanceResource resource) {
        Performance entity = performanceRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        resource = new PerformanceResource(entity);
        return resource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    PerformanceResource delete(@PathVariable Integer id) {
        Performance entity = performanceRepository.delete(id);
        if (entity == null) return null;
        PerformanceResource resource = new PerformanceResource(entity);
        return resource;
    }

    private Set<Performance> intersection(Set<Performance> A, Set<Performance> B)
    {
        ArrayList<Performance> preanswer = new ArrayList<>();
        for(Performance a : A)
        {
            for(Performance b : B)
            {
                if (a.getId()==(b.getId()))
                {
                    preanswer.add(b);
                }
            }
        }
        return new HashSet<Performance>(preanswer);
    }
}