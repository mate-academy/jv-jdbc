package mate.jdbc.model;

public class Manufacturer {
    private String name;
    private String country;
    private long id;
    /*
    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Manufacturer(String name, String country, long id) {
        this.name = name;
        this.country = country;
        this.id = id;
    }



    public Manufacturer() {

    }

     */

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Manufacturer{"
                + "name = '" + name + '\''
                + ", country = '" + country + '\''
                + ", id = " + id
                + '}';
    }
}
