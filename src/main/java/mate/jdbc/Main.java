package mate.jdbc;

import com.sun.jdi.connect.spi.Connection;
import mate.jdbc.util.ConnectionToDbUtil;
import mate.jdbc.util.DbPropertiesFileReader;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties dbproperties = DbPropertiesFileReader
                .getPropertiesFrom("src/main/resources/DBProperties");

    }
}
