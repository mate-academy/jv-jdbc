package mate.jdbc.model;

public class Manufacturer {
    private String name;
    private String country;
    private long id;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Manufacturer{"
                + "name = '" + name + '\''
                + ", country = '" + country + '\''
                + ", id = " + id
                + '}';
    }
}
