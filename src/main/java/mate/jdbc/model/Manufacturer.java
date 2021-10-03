package mate.jdbc.model;

import java.util.Objects;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Manufacturer)) {
            return false;
        }
        Manufacturer that = (Manufacturer) o;
        return id.equals(that.id) && name.equals(that.name) && country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

    @Override
    public String toString() {
        return "Id: " + id + " Name: " + name + " Country: " + country;
    }
}
