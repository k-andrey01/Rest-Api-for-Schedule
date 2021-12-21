package rssapijava.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import rssapijava.entity.Language;
import rssapijava.entity.Performance;

public class LanguageResource {
    private Integer id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PerformanceResource[] performance;

    public LanguageResource() {}

    public LanguageResource(Language language) {
        this.id = language.getId();
        this.name = language.getName();
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public PerformanceResource[] getPerformance() { return performance; }

    public void setPerformance(PerformanceResource[] performance) { this.performance = performance; }

    public Language toEntity() {
        return new Language(
                this.id,
                this.name
        );
    }
}
