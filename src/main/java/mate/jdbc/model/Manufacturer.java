package mate.jdbc.model;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer() {

    }

    public Manufacturer(String name, String country) {
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return builder.append("Id: ").append(id).append(System.lineSeparator())
                .append("Name: ").append(name).append(System.lineSeparator())
                .append("Country: ").append(country).append(System.lineSeparator())
                .toString();
    }
}
