package mate.jdbc.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Manufacturer {
    private Long id;
    private String name;
    private String country;
}
