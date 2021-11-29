package mate.jdbc.services.parsers;

import java.sql.ResultSet;
import java.util.List;
import mate.jdbc.models.DbModel;

public interface DbModelParser {
    List<? extends DbModel> parse(ResultSet resultSet);
}
