package rssapijava.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import rssapijava.entity.City;
import rssapijava.entity.Place;

public class CityResource {
    private Integer id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PlaceResource[] place;

    public CityResource() {}

    public CityResource(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public PlaceResource[] getPlace() { return place; }

    public void setPlace(PlaceResource[] place) { this.place = place; }

    public City toEntity() {
        return new City(
                this.id,
                this.name
        );
    }
}
