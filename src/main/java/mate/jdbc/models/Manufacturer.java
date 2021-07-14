package mate.jdbc.models;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer() {
        id = 3L;
        name = "Unnamed";
        country = "Unknown";
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
    public String toString() {
        return new StringBuilder((int) id.longValue()).append(" ")
                .append(name).append(" ")
                .append(country).toString();
    }
}
