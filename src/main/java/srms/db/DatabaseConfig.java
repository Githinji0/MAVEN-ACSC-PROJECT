package srms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DatabaseConfig {

   
    private static final String MYSQL_HOST = System.getenv().getOrDefault("SRMS_DB_HOST", "localhost");
    private static final String MYSQL_PORT = System.getenv().getOrDefault("SRMS_DB_PORT", "3306");
    private static final String MYSQL_USER = System.getenv().getOrDefault("SRMS_DB_USER", "root");
    private static final String MYSQL_PASS = System.getenv().getOrDefault("SRMS_DB_PASS", ""); 
    public static final String DB_NAME = System.getenv().getOrDefault("SRMS_DB_NAME", "srms_db");

    private static String serverUrl() {
        return "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    }

    private static String dbUrl() {
        return "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + DB_NAME + "?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    }

    public static void initialize() {
        try {
            // Connect to server (no database) and create database if missing
            try (Connection conn = DriverManager.getConnection(serverUrl(), MYSQL_USER, MYSQL_PASS);
                 Statement stmt = conn.createStatement()) {

                String createDb = "CREATE DATABASE IF NOT EXISTS `" + DB_NAME + "` CHARACTER SET = 'utf8mb4' COLLATE = 'utf8mb4_unicode_ci';";
                stmt.executeUpdate(createDb);
                System.out.println("Database created/verified: " + DB_NAME);
            }

           
            try (Connection conn = DriverManager.getConnection(dbUrl(), MYSQL_USER, MYSQL_PASS);
                 Statement stmt = conn.createStatement()) {

                // Students table
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS students (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        registration_number VARCHAR(100) UNIQUE NOT NULL,
                        first_name VARCHAR(100),
                        last_name VARCHAR(100),
                        email VARCHAR(200),
                        date_of_birth DATE,
                        enrollment_date DATE,
                        department VARCHAR(100),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );
                """);

                // Courses table
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS courses (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        course_code VARCHAR(50) UNIQUE NOT NULL,
                        course_title VARCHAR(200),
                        credits INT,
                        course_description TEXT,
                        prerequisites TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    );
                """);

               
                stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS enrollments (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        student_id INT NOT NULL,
                        course_id INT NOT NULL,
                        enrollment_date DATE,
                        grade VARCHAR(10),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                        FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
                    );
                """);

                System.out.println("Tables created/verified (students, courses, enrollments).");
            }

        } catch (Exception e) {
            System.err.println("Database initialization failed:");
            e.printStackTrace();
        }
    }
}
