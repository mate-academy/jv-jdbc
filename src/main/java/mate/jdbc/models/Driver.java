package mate.jdbc.models;

import java.util.List;
import mate.jdbc.lib.Dao;

@Dao
public class Driver {
    private Long id;
    private String firstName;
    private String lastName;
    private List<Car> cars;
}
