package dao;

import java.util.List;
import model.Manufacturer;

public interface ManufacturerDao {
    List<Manufacturer> getAll();

    Manufacturer create(Manufacturer manufacturer);
}
