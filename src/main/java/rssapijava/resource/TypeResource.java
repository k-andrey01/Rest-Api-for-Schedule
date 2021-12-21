package rssapijava.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import rssapijava.entity.Performance;
import rssapijava.entity.Type;

public class TypeResource {
    private Integer id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PerformanceResource[] performance;

    public TypeResource() {}

    public TypeResource(Type type) {
        this.id = type.getId();
        this.name = type.getName();
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public PerformanceResource[] getPerformance() { return performance; }

    public void setPerformance(PerformanceResource[] performance) { this.performance = performance; }

    public Type toEntity() {
        return new Type(
                this.id,
                this.name
        );
    }
}
