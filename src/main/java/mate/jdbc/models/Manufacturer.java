package mate.jdbc.models;

import mate.jdbc.lib.Dao;

@Dao
public class Manufacturer {
    private Long id;
    private String name;
    private String country;

    public Manufacturer(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}'+
                System.lineSeparator();
    }
}
