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

    public Car(Long id, String name, String color, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.manufacturer = manufacturer;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
