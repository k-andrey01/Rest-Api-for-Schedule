package rssapijava.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import rssapijava.entity.Performance;
import rssapijava.entity.Schedule;

import java.sql.Timestamp;

public class ScheduleResource extends BaseResource {
    private Integer id;
    private Integer placeId;
    private Integer performanceId;
    private Timestamp date_time;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PlaceResource place;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PerformanceResource performance;

    public ScheduleResource() {}

    public ScheduleResource(Schedule schedule) {
        this.id = schedule.getId();
        this.placeId = schedule.getPlaceId();
        this.performanceId = schedule.getPerformanceId();
        this.date_time = schedule.getDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public Integer getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(Integer performanceId) {
        this.performanceId = performanceId;
    }

    public Timestamp getDate_time() {
        return date_time;
    }

    public void setDate_time(Timestamp date_time) {
        this.date_time = date_time;
    }

    public PlaceResource getPlace() { return this.place; }

    public void setPlace(PlaceResource place) { this.place = place; }

    public PerformanceResource getPerformance() { return this.performance; }

    public void setPerformance(PerformanceResource performance) { this.performance = performance; }

    public Schedule toEntity() {
        return new Schedule(
                this.id,
                this.placeId,
                this.performanceId,
                this.date_time
        );
    }
}
