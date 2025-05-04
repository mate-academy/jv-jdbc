package mate.jdbc.model;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Long getId() {
        return id;
    }

    public Manufacturer setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Manufacturer setName(String name) {
        this.name = name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Manufacturer setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        return "Manufacturer{id=" + id + ", name='" + name + '\'' + ", country='" + country
                + '\'' + '}';
    }
}
