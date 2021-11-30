package mate.jdbc.models;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer() {
    }

    private Manufacturer(String name, String country, Long id) {
        this.name = name;
        this.country = country;
        this.id = id;
    }

    public static Manufacturer of(String name, String country, Long id) {
        return new Manufacturer(name, country, id);
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
