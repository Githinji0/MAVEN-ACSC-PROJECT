package srms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String URL_TEMPLATE = "jdbc:mysql://%s:%s/%s?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";

    private static final String HOST = System.getenv().getOrDefault("SRMS_DB_HOST", "localhost");
    private static final String PORT = System.getenv().getOrDefault("SRMS_DB_PORT", "3306");
    private static final String DBNAME = DatabaseConfig.DB_NAME;
    private static final String USER = System.getenv().getOrDefault("SRMS_DB_USER", "root");
    private static final String PASS = System.getenv().getOrDefault("SRMS_DB_PASS", "");

    private static final String URL = String.format(URL_TEMPLATE, HOST, PORT, DBNAME);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
