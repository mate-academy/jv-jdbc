package mate.jdbc.DAO;

import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public class ManufacturerDao {
    public Manufacturer create(Manufacturer manufacturer){

        return manufacturer;
    }
    public Optional<Manufacturer> get(Long id){

        return null;
    }
    public List<Manufacturer> getAll(){

        return null;
    }
    public Manufacturer update(Manufacturer manufacturer){

        return manufacturer;
    }
    public boolean delete(Long id){

        return false;
    }
}
