# HW 02

## Project description
The aim of current and following lessons is to create a basic `taxi service` project.
Its' functionality should include: 
* create new car/manufacturer 
* display all drivers/cars/cars by driver/manufacturers 
* add a driver to car 

First, you will create software representation of `driver`, `car`, `manufacturer` 
and relations between them. To make this data long-term accessible DB is a must-have. And of course, we will add a User
Interface layer to our app so you can easily manipulate the data. <br>
But let's take one step at a time: in the current course, you will take care of the data storing part by implementing 
a layer in the application, here is your first task: 


- Establish connection to your Database.🎯
- Create `init_db.sql` file in `src/main/resources` folder.🎯
- Create `Manufacturer` model.🎯
- Create DAO  layer for `Manufacturer` model. Below you can see the list of required methods.🎯
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

🔥Use Statement.RETURN_GENERATED_KEYS only in create statement, it’s not needed in other methods.🔥

- You're already given an injector and `@Dao` annotation. Do not forget to use it for Dao implementations.🎯
- Return [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) when you can return null in DAO.🎯
  For example: ```public Optional<Manufacturer> get(Long id);```
- In the `main` method call CRUD methods. It may look like:🎯
```java
public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        // initialize field values using setters or constructor
        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
    }
}
```
**WARNING!!!** Path to your project must contain only english letters. Also, it mustn't contain spaces. In other case `Injector` won't work correctly.
- Your table should be named `manufacturers` and contain these columns: `id`, `name`, `country`, and `is_deleted`.

### Create custom exception 🎯
`e.printStackTrace()` - is a bad practice! Let's create custom exception `DataProcessingException`
and constructor with two parameters: `String message` and `Throwable ex`.  
It should be extended from `RuntimeException`. You should rethrow this exception in `catch` block on dao layer.

Use in formative messages for exceptions.<br>
     Wrong: <br>
           ` throw new DataProcessingException("Can't get manufacturer", e);` <br>
      Good:<br>
`throw new DataProcessingException("Can't get manufacturer by id " + id, e);`
          `throw new DataProcessingException("Can't get manufacturer by id " + id, e);`<br>
          `throw new DataProcessingException("Can't insert manufacturer " + manufacturer, e);`<br>
#### DB connection error: 
If you can't connect to your db because of this error: <br>
`The server time zone value ‘EEST’ is unrecognized or represents more than one time zone`. <br>
Try to set timezone explicitly in your connection URL. <br>
Example: <br>
`...localhost:3306/your_schema?serverTimezone=UTC` <br>
Or you can set a timezone in MySql directly by running command: `SET GLOBAL time_zone = '+3:00'`;

__You can check yourself using this__ [checklist](https://mate-academy.github.io/jv-program-common-mistakes/java-JDBC/jdbc-intro/JDBC-intro_checklist.html)
