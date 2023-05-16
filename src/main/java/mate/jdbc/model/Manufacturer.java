package mate.jdbc.model;

import java.util.Objects;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Manufacturer manufacturer = (Manufacturer) object;
        return Objects.equals(id, manufacturer.getId())
                && Objects.equals(name, manufacturer.getName())
                && Objects.equals(country, manufacturer.getCountry());
    }

    @Override
    public String toString() {
        return "Manufacturer{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", country='" + country + '\'' + '}';
    }
}
