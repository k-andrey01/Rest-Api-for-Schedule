package rssapijava.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import rssapijava.entity.Place;

public class PlaceResource extends BaseResource {
    private Integer id;
    private Integer cityId;
    private String name;
    private String street;
    private String house;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CityResource city;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ScheduleResource[] schedule;

    public PlaceResource() {}

    public PlaceResource(Place place) {
        this.id = place.getId();
        this.cityId = place.getCityId();
        this.name = place.getName();
        this.street = place.getStreet();
        this.house = place.getHouse();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public CityResource getCity() { return this.city; }

    public void setCity(CityResource city) { this.city = city; }

    public ScheduleResource[] getSchedule() { return schedule; }

    public void setSchedule(ScheduleResource[] schedule) { this.schedule = schedule; }

    public Place toEntity() {
        return new Place(
                this.id,
                this.cityId,
                this.name,
                this.street,
                this.house
        );
    }
}
