package mate.jdbc.model;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
