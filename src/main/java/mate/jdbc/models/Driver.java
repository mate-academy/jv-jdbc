package mate.jdbc.models;

import mate.jdbc.lib.Dao;
import mate.jdbc.lib.Inject;
import java.util.List;

@Dao
public class Driver {
    private  Long id;
    private String firstName;
    private String lastName;
    @Inject
    List<Car> cars;
}
