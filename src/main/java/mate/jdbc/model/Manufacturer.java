package mate.jdbc.model;

import java.util.Objects;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer(String name, String country) {
        this.country = country;
        this.name = name;
    }

    public Manufacturer() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Manufacturer manufacturer = (Manufacturer) obj;
        return Objects.equals(name, manufacturer.name)
                && Objects.equals(country, manufacturer.country)
                && Objects.equals(id, manufacturer.id);
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name + " country: " + country;
    }
}
