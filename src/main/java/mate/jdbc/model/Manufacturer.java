package mate.jdbc.model;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Manufacturer{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", country='"
                + country
                + '\''
                + '}';
    }
}
