package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Repository {

    default Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/lotto_game" +
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String id = "choi";
        String pw = "123456";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, id, pw);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    default <T extends AutoCloseable> void closeConnection(T t) {
        if (t != null) {
            try {
                t.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
    }
}
