package mate.jdbc.models;

import java.util.Objects;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Manufacturer(Long id, String name, String country) {
        this(name, country);
        this.id = id;
    }

    public Manufacturer(){

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
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Manufacturer current = (Manufacturer) obj;
        return Objects.equals(id, current.id)
                && Objects.equals(name, current.name)
                && Objects.equals(country, current.country);
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
