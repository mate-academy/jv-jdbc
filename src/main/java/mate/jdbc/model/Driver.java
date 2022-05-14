package mate.jdbc.model;

public class Driver {
    private Long id;
    private String firstName;
    private String lastName;
    private String licenseNumber;

    public Driver(Long id, String firstName, String lastName, String licenseNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseNumber = licenseNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        return "Driver{"
                + "id=" + id
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", licenseNumber='" + licenseNumber + '\''
                + '}';
    }
}
