package mate.jdbc.dto;

import java.util.Objects;

public class ResultSetEntity {
    private long id;
    private String name;
    private String country;

    public ResultSetEntity(long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return "ResultSet{" + "id=" + id + ", name='" + name
                + '\'' + ", country='" + country + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResultSetEntity resultSet = (ResultSetEntity) o;
        return id == resultSet.id
                && Objects.equals(name, resultSet.name)
                && Objects.equals(country, resultSet.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
