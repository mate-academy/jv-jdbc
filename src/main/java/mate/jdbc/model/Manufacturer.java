package mate.jdbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Manufacturer {
    private Long id;
    private String name;
    private String country;
    private boolean isDeleted;

    public Manufacturer(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }
}
