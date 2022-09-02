package mate.jdbc.model;

public class Manufacturer implements Cloneable {
    private Long id;
    private String name;
    private String country;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
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
    public String toString() {
        return "Manufacturer{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", country='" + country + '\''
                + '}';
    }

    @Override
    public Manufacturer clone() {
        Manufacturer user;
        try {
            user = (Manufacturer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Can't clone Manufacturer", e);
        }
        return user;
    }
}
