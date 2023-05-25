Целью текущего и последующих уроков является создание базового проекта службы такси. Его функционал должен включать:

* создать новую машину/производителя
* отображать всех водителей/автомобилей/автомобилей по драйверам/производителям
* добавить водителя в машину

Сначала вы создадите программное представление водителя, автомобиля, производителя и отношений между ними. 
Чтобы сделать эти данные доступными в долгосрочной перспективе, необходима БД. И, конечно же, 
мы добавим в наше приложение слой пользовательского интерфейса, чтобы вы могли легко манипулировать данными.

Но давайте пошагово: в нынешнем курсе, вы позаботитесь о части хранения данных, реализовав слой в приложении, вот ваша первая задача:

* Установите соединение с вашей базой данных.
* Создайте файл init_db.sql в папке src/main/resources.
* Создать модель производителя.
* Создайте слой DAO для модели производителя. Ниже вы можете увидеть список необходимых методов.
* Вы уже получили инжектор и аннотацию @Dao. Не забудьте использовать его для реализации Дао.
* Возврат необязательно, когда вы можете вернуть ноль в DAO. Например: public Optional<Manufacturer> get(Long id);
* В основном методе вызовите методы CRUD. Это может выглядеть так:

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

ПРЕДУПРЕЖДЕНИЕ!!! Путь к вашему проекту должен содержать только английские буквы. 
Кроме того, он не должен содержать пробелов. В противном случае инжектор не будет работать корректно.

* Ваша таблица должна быть названа производителями и содержать следующие столбцы: идентификатор, имя, страна и is_deleted.

Структура классов Java:

* Manufacturer

public class Manufacturer {
private Long id;
private String name;
private String country;
}

ManufacturerDao methods:

- Manufacturer create(Manufacturer manufacturer);
- Optional<Manufacturer> get(Long id);
- List<Manufacturer> getAll();
- Manufacturer update(Manufacturer manufacturer);
- boolean delete(Long id);

Create custom exception

e.printStackTrace() - плохая практика! Давайте создадим пользовательское исключение DataProcessingException и конструктор с двумя параметрами: String message и Throwable ex.
Он должен быть расширен из RuntimeException. Вы должны повторно вызвать это исключение в блоке catch на уровне dao.

DB connection error:

Если вы не можете подключиться к своей базе данных из-за этой ошибки:
Значение часового пояса сервера «EEST» не распознано или представляет более одного часового пояса.
Попробуйте явно указать часовой пояс в URL-адресе вашего подключения.
Пример:
...localhost:3306/your_schema?serverTimezone=UTC
Или вы можете установить часовой пояс непосредственно в MySql, выполнив команду: SET GLOBAL time_zone = '+3:00';
