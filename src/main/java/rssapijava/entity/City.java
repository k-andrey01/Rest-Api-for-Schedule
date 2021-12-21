package rssapijava.entity;

public class City extends BaseEntity{
    public String name;

    public City(Integer id, String name){
        super(id);
        this.name = name;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
