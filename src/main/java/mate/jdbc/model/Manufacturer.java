package mate.jdbc.model;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    private Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Manufacturer(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public static Manufacturer of(String name, String country) {
        return new Manufacturer(name, country);
    }

    public static Manufacturer of(Long id, String name, String country) {
        return new Manufacturer(id, name, country);
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

    @Override
    public String toString() {
        return "Manufacturer{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", country='" + country + '\''
                + '}';
    }
}
