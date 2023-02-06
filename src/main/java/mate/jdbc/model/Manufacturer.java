package mate.jdbc.model;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;
    private boolean deleted;

    @Override
    public String toString() {
        return "Manufacturer{"
                + "id=" + id + ", name='" + name + '\''
                + ", country='" + country + '\'' + '}';
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
}
