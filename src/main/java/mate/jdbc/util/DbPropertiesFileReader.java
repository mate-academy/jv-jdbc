package mate.jdbc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

public class DbPropertiesFileReader {
    private static final int PROPERTIES_FORMAT_DATA_DESIGNATOR = 0;
    private static final int PROPERTIES_FORMAT_DATA_VALUE = 1;
    private static final String PROPERTIES_FORMAT_DATA_SPLITTER = ":";
    private static final String PROPERTIES_LOGIN_DESIGNATOR = "user";
    private static final String PROPERTIES_PASSWORD_DESIGNATOR = "password";

    public static Properties getPropertiesFrom(String filePath) {
        Path path = Path.of(filePath);
        List<String> linesFromFile;
        Properties properties = new Properties();

        try {
            linesFromFile = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Can`t read properties file", e);
        }

        linesFromFile.forEach(s -> formatProperties(s, properties));
        return properties;
    }

    private static void formatProperties(String line, Properties properties) {
        String[] dataFromLine = line.split(PROPERTIES_FORMAT_DATA_SPLITTER);
        putData(dataFromLine, properties);
    }

    private static void putData(String[] dataFromLine, Properties properties) {
        switch (dataFromLine[PROPERTIES_FORMAT_DATA_DESIGNATOR]) {
            case PROPERTIES_LOGIN_DESIGNATOR:
                properties.put(PROPERTIES_LOGIN_DESIGNATOR,
                    dataFromLine[PROPERTIES_FORMAT_DATA_VALUE]);
                break;
            case PROPERTIES_PASSWORD_DESIGNATOR:
                properties.put(PROPERTIES_PASSWORD_DESIGNATOR,
                        dataFromLine[PROPERTIES_FORMAT_DATA_VALUE]);
                break;
            default:
                throw new RuntimeException("Can't get properties from file");

        }
    }
}
