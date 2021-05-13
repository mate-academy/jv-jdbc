# HW 02

- Establish connection to your Database.
- Create `Manufacturer` model.
- Create DAO  layer for `Manufacturer` model. Below you can see the list of required methods.
- You're already given an injector and `@Dao` annotation. Do not forget to use it for Dao implementations.
- Return [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) when you can return null in DAO.
  For example: ```public Optional<Manufacturer> get(Long id);```
- In the `main` method call CRUD methods. It may look like:
```java
public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        // initialize field values using setters or constructor
        manufacturerDao.save(manufacturer);
        // same for all other crud methods
    }
}
```
- Your table should be named `manufacturers` and contain these columns: `id`, `name`, `country`.
### Java classes structure:
- Manufacturer
```java
public class Manufacturer {
    private Long id;
    private String name;
    private String country;
}
```

### ManufacturerDao methods:
    - Manufacturer create(Manufacturer manufacturer);
    - Optional<Manufacturer> get(Long id);
    - List<Manufacturer> getAll();
    - Manufacturer update(Manufacturer manufacturer);
    - boolean delete(Long id);

__You can check yourself using this__ [checklist](https://mate-academy.github.io/jv-program-common-mistakes/java-JDBC/jdbc-intro/JDBC-intro_checklist.html)
