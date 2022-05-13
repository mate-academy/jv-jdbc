package mate.jdbc.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import mate.jdbc.exception.DataProcessingException;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Manufacturer getManufacturer(ResultSet resultSet) {
        Manufacturer manufacturer = new Manufacturer();
        try {
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject("id", Long.class));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                return manufacturer;
            }
        } catch (SQLException e) {
            throw new
              DataProcessingException("can't create object based on data from resultSet", e);
        }
        return manufacturer;
    }

    @Override
    public String toString() {
        return "Manufacturer{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", country='" + country + '\''
                + '}';
    }
}
