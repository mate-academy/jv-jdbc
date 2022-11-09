package mate.jdbc.model;

public class Manufacturer {
    Long id;
    private String country;
    private String companyName;

    public Manufacturer(Long id, String companyName, String country) {
        this.id = id;
        this.country = country;
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
