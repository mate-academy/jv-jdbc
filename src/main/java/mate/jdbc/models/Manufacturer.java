package mate.jdbc.models;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer(String name, String country, Long id) {
        this.name = name;
        this.country = country;
        this.id = id;
    }
}
