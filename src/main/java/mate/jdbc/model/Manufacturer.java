package mate.jdbc.model;

import lombok.Data;

@Data
public class Manufacturer {
    private Long id;
    private String name;
    private String country;
    private boolean isDeleted;

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Manufacturer(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
