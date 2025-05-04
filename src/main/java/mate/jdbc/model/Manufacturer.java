package mate.jdbc.model;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer() {
    }

    public Manufacturer(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
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
    public boolean equals(Object manufacturer) {
        if (this == manufacturer) {
            return true;
        }
        if (manufacturer == null || getClass() != manufacturer.getClass()) {
            return false;
        }
        Manufacturer current = (Manufacturer) manufacturer;
        return id.equals(current.id) && name.equals(current.name)
                && country.equals(current.country);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = (int) (31 * result + id);
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (country == null ? 0 : country.hashCode());
        return result;
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
