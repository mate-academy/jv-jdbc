package mate.jdbc.model;

public class Manufacturer {
    private long id;
    private String name;
    private String country;
    private boolean isDeleted;

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }
}
