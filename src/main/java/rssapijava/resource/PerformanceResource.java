package rssapijava.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import rssapijava.entity.Performance;
import rssapijava.entity.Place;

public class PerformanceResource extends BaseResource {
    private Integer id;
    private Integer typeId;
    private Integer languageId;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TypeResource type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LanguageResource language;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ScheduleResource[] schedule;

    public PerformanceResource() {}

    public PerformanceResource(Performance performance) {
        this.id = performance.getId();
        this.typeId = performance.getTypeId();
        this.name = performance.getName();
        this.languageId = performance.getLanguageId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public TypeResource getType() { return this.type; }

    public void setType(TypeResource type) { this.type = type; }

    public LanguageResource getLanguage() { return this.language; }

    public void setLanguage(LanguageResource language) { this.language = language; }

    public ScheduleResource[] getSchedule() { return schedule; }

    public void setSchedule(ScheduleResource[] schedule) { this.schedule = schedule; }

    public Performance toEntity() {
        return new Performance(
                this.id,
                this.typeId,
                this.languageId,
                this.name
        );
    }
}
