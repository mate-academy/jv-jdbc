package mate.jdbc.model;

public class Manufacturer {

    private Long id;
    private String name;
    private String country;
    private boolean id_delete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isId_delete() {
        return id_delete;
    }

    public void setId_delete(boolean id_delete) {
        this.id_delete = id_delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
