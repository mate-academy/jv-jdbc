package mate.jdbc.models;

import mate.jdbc.lib.Dao;
import mate.jdbc.lib.Inject;

@Dao
public class Car {
    private Long id;
    private String name;
    private String color;
    @Inject
    private Manufacturer manufacturer;
}
