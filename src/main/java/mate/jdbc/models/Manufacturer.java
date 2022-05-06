package mate.jdbc.models;

public class Manufacturer {
    private Long id;
    private String name;
    private String country;
    private boolean isDeleted;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Manufacturer{id=" + getId()
                + ", name=" + getName()
                + ", country=" + getCountry()
                + ", is_deleted=" + getIsDeleted() + "}";
    }
}
