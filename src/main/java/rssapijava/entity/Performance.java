package rssapijava.entity;

public class Performance extends BaseEntity {
    private Integer type_id;
    private Integer language_id;
    private String name;

    public Performance(Integer id, Integer type_id, Integer language_id, String name) {
        super(id);
        this.type_id = type_id;
        this.name = name;
        this.language_id = language_id;
    }

    public Integer getTypeId() {
        return type_id;
    }

    public void setTypeId(Integer type_id) {
        this.type_id = type_id;
    }

    public Integer getLanguageId() {
        return language_id;
    }

    public void setLanguageId(Integer language_id) {
        this.language_id = language_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
