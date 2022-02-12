package mate.jdbc.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Manufacturer {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String country;
}
