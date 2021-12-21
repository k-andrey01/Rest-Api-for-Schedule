package rssapijava.entity;

import java.sql.Timestamp;

public class Schedule extends BaseEntity {
    private Integer place_id;
    private Integer performance_id;
    private Timestamp date_time;

    public Schedule(Integer id, Integer place_id, Integer performance_id, Timestamp date_time) {
        super(id);
        this.place_id = place_id;
        this.performance_id = performance_id;
        this.date_time = date_time;
    }

    public Integer getPlaceId() {
        return place_id;
    }

    public void setPlaceId(Integer place_id) {
        this.place_id = place_id;
    }

    public Integer getPerformanceId() {
        return performance_id;
    }

    public void setPerformanceId(Integer performance_id) {
        this.performance_id = performance_id;
    }

    public Timestamp getDate() {
        return date_time;
    }

    public void setDate(Timestamp date_time) {
        this.date_time = date_time;
    }
}