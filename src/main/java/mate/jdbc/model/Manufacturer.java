package mate.jdbc.model;

public class Manufacturer {
    private final String name;
    private final String country;
    private Long id;

    public Manufacturer(final String name, final String country) {
        this.name = name;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        final String sb = "Manufacturer{" + "id=" + id
                + ", name='" + name + '\''
                + ", country='" + country + '\''
                + '}';
        return sb;
    }
}
