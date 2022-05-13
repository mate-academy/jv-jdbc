package mate.jdbc.model;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;
    private boolean status;

    public Manufacturer() {
    }

    public Manufacturer(String name, String country, boolean status) {
        this.name = name;
        this.country = country;
        this.status = status;
    }

    public Manufacturer(Long id, String name, String country, boolean status) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.status = status;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
