# HW 02

## Project description
The aim of current and following lessons is to create a basic `taxi service` project.
Its' functionality should include: 
* create new car/manufacturer
* display all drivers/cars/cars by driver/manufacturers
* add a driver to car

First, you will create software representation of `driver`, `car`, `manufacturer` 
and relations between them. To make this data long-term accessible DB is a must-have. And of course, we will add a User Interface layer to our app so you can easily manipulate the data. <br>
But let's take one step at a time: in the current course, you will take care of the data storing part by implementing a layer in the application, here is your first task: 


- Establish connection to your Database.
- Create `init_db.sql` file in `src/main/resources` folder.
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
        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
    }
}
```
**WARNING!!!** Path to your project must contain only english letters. Also, it mustn't contain spaces. In other case `Injector` won't work correctly.
- Your table should be named `manufacturers` and contain these columns: `id`, `name`, `country`, and `is_deleted`.
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
    
### Create custom exception
`e.printStackTrace()` - is a bad practice! Let's create custom exception `DataProcessingException`
and constructor with two parameters: `String message` and `Throwable ex`.  
It should be extended from `RuntimeException`. You should rethrow this exception in `catch` block on dao layer.
    
#### DB connection error: 
If you can't connect to your db because of this error: <br>
`The server time zone value ‘EEST’ is unrecognized or represents more than one time zone`. <br>
Try to set timezone explicitly in your connection URL. <br>
Example: <br>
`...localhost:3306/your_schema?serverTimezone=UTC` <br>
Or you can set a timezone in MySql directly by running command: `SET GLOBAL time_zone = '+3:00'`;

__You can check yourself using this__ [checklist](https://mate-academy.github.io/jv-program-common-mistakes/java-JDBC/jdbc-intro/JDBC-intro_checklist.html)

/*
# HW 02

## Опис проекту
Метою поточного та наступних уроків є створення базового проекту «служби таксі».
Його функціональність повинна включати:
* створити новий автомобіль/виробника
* відображення всіх водіїв/автомобілів/автомобілів за водіями/виробниками
* додати водія до автомобіля

Спочатку ви створите програмне представлення `водія`, `автомобіля`, `виробника`
і відносини між ними. Щоб зробити ці дані довгостроково доступними, необхідно мати БД. І звичайно, 
ми додамо рівень інтерфейсу користувача до нашої програми,
щоб ви могли легко маніпулювати даними. <br>
Але давайте крок за кроком: у поточному курсі ви подбаєте про частину зберігання даних,
реалізувавши рівень у програмі, ось ваше перше завдання:


- Встановіть підключення до вашої бази даних.
- Створіть файл `init_db.sql` в папці `src/main/resources`.
- Створення моделі `Manufacturer`.
  — Створити рівень DAO для моделі `Manufacturer`. Нижче ви можете побачити список необхідних методів.
- Ви вже отримали інжектор і анотацію @Dao. Не забувайте використовувати його для реалізацій Dao.
- Повернути [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html), 
- якщо ви можете повернути значення null у DAO.
  Наприклад: ```public Optional<Manufacturer> get(Long id);```
- У методі `main` виклик методів CRUD. Це може виглядати так:
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
**УВАГА!!!** Шлях до вашого проекту має містити лише англійські літери. 
Крім того, він не повинен містити пробілів. 
В іншому випадку `Інжектор` не працюватиме належним чином.
- Ваша таблиця має мати назву `manufacturers` і містити такі стовпці: `id`, `name`, `country` і `is_deleted`.
### Структура класів Java:
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

### Створити спеціальний виняток
`e.printStackTrace()` - це погана практика! Давайте створимо спеціальний виняток `DataProcessingException`
і конструктор із двома параметрами: `String message` і `Throwable ex`.
Його слід розширити з `RuntimeException`. Ви повинні повторно створити цей виняток у блоці catch на рівні dao.

#### Помилка підключення до БД:
Якщо ви не можете підключитися до своєї бази даних через цю помилку: <br>
`Значення часового поясу сервера 'EEST' не розпізнано або представляє більше ніж один часовий пояс'. <br>
Спробуйте чітко встановити часовий пояс у URL-адресі підключення. <br>
Приклад: <br>
`...localhost:3306/your_schema?serverTimezone=UTC` <br>
Або ви можете встановити часовий пояс безпосередньо в MySql, виконавши команду: `SET GLOBAL time_zone = '+3:00'`;

']\[[__Ви можете перевірити себе за допомогою цього__ [контрольного списку](https://mate-academy.github.io/jv-program-common-mistakes/java-JDBC/jdbc-intro/JDBC-intro_checklist.html)
# HW 02
*/