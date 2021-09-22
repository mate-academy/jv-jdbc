package mate.jdbc.model;

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

    public String toString() {
        return "id: " + this.id + ", name: " + this.name + " , country: " + this.country + ".";
    }
}
