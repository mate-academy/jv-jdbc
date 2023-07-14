package mate.jdbc.model;

import java.util.List;

public interface LiteraryFormatDao {
    List<LiteraryFormat> getAll();
    LiteraryFormat create(LiteraryFormat format);

    LiteraryFormat get(Long id);
    LiteraryFormat update(LiteraryFormat literaryFormat);
    //передавати в якому вже є певний літературний ідентифікатор


    boolean delete(Long id);
}
