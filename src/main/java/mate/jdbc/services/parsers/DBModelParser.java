package mate.jdbc.services.parsers;

import java.sql.ResultSet;
import java.util.List;
import mate.jdbc.models.DBModel;

public interface DBModelParser {
    List<? extends DBModel> parse(ResultSet resultSet);
}
