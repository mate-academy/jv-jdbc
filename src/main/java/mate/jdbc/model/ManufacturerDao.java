package mate.jdbc.model;

import java.util.*;

public interface ManufacturerDao {
    List<Manufacturer> getAll();
    Manufacturer create(Manufacturer manufacturer);
    Manufacturer get(Long id);
    Manufacturer update(Manufacturer manufacturer);
    //передавати в якому вже є певний літературний ідентифікатор
    boolean delete(Long id);

    /*
- Manufacturer create(Manufacturer manufacturer);
- Optional<Manufacturer> get(Long id);
- List<Manufacturer> getAll();
- Manufacturer update(Manufacturer manufacturer);
- boolean delete(Long id);
    */


//Return Optional when you can return null in DAO. For example: public Optional<Manufacturer> get(Long id);

}
