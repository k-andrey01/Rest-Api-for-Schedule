package rssapijava.controller;

import org.springframework.web.bind.annotation.*;
import rssapijava.entity.Language;
import rssapijava.repository.LanguageRepository;
import rssapijava.repository.PerformanceRepository;
import rssapijava.resource.LanguageResource;
import rssapijava.resource.PerformanceResource;
import rssapijava.resource.PlaceResource;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/language")
public class LanguageController {
    private final LanguageRepository languageRepository;
    private final PerformanceRepository performanceRepository;

    public LanguageController(LanguageRepository languageRepository, PerformanceRepository performanceRepository) {
        this.languageRepository = languageRepository;
        this.performanceRepository = performanceRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    LanguageResource[] getAll(@RequestParam(required = false) Object expand) {
        return Arrays.stream(languageRepository.select())
                .map(entity -> {
                    LanguageResource resource = new LanguageResource(entity);
                    if (expand != null)
                        resource.setPerformance(
                                Arrays.stream(performanceRepository.selectByLanguageId(entity.getId()))
                                        .map(e -> new PerformanceResource(e))
                                        .toArray(PerformanceResource[]::new)
                        );
                    return resource;
                })
                .toArray(LanguageResource[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    LanguageResource get(@PathVariable Integer id,
                     @RequestParam(required = false) Object expand) {
        Language entity = languageRepository.select(id);
        if (entity == null) return null;
        LanguageResource resource = new LanguageResource(entity);
        if (expand != null)
            resource.setPerformance(
                    Arrays.stream(performanceRepository.selectByLanguageId(entity.getId()))
                            .map(e -> new PerformanceResource(e))
                            .toArray(PerformanceResource[]::new)
            );
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    LanguageResource post(@RequestBody LanguageResource resource) {
        Language entity = languageRepository.insert(resource.toEntity());
        if (entity == null) return null;
        return new LanguageResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    LanguageResource put(@PathVariable Integer id,
                     @RequestBody LanguageResource resource) {
        Language entity = languageRepository.update(id, resource.toEntity());
        if (entity == null) return null;
        return new LanguageResource(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    LanguageResource delete(@PathVariable Integer id) {
        Language entity = languageRepository.delete(id);
        if (entity == null) return null;
        return new LanguageResource(entity);
    }
}

