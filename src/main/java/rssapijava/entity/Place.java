package rssapijava.entity;

public class Place extends BaseEntity {
    private Integer city_id;
    private String name;
    private String street;
    private String house;

    public Place(Integer id, Integer city_id, String name, String street, String house) {
        super(id);
        this.city_id = city_id;
        this.name = name;
        this.street = street;
        this.house = house;
    }

    public Integer getCityId() {
        return city_id;
    }

    public void setCityId(Integer city_id) {
        this.city_id = city_id;
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

    public String getHouse() { return house; }

    public void setHouse(String house) {
        this.house = house;
    }
}

