package mate.jdbc.dao;

import java.util.List;
import mate.jdbc.LiteraryFormat;

public interface LiteraryFormatDao {
    List<LiteraryFormat> getAll();
    LiteraryFormat create(LiteraryFormat format);
}
