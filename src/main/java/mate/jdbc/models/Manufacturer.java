package mate.jdbc.models;

public class Manufacturer {
    private String name;
    private String country;
    private Long id;

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Manufacturer(String name, String country, Long id) {
        this.name = name;
        this.country = country;
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "name: " + name + ", country: " + country + ", id: " + id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
