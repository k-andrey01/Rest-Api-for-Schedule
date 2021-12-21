package rssapijava.entity;

public class Language extends BaseEntity{
    public String name;

    public Language(Integer id, String name){
        super(id);
        this.name = name;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
