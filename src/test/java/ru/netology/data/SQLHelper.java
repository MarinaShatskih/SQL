package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        // Добавлены параметры ?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "app",
                "pass"
        );
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        // Увеличим паузу до 1000мс, так как Docker может работать медленно
        Thread.sleep(1000);
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            return QUERY_RUNNER.query(conn, codeSQL, new BeanHandler<>(DataHelper.VerificationCode.class));
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var connection = getConn()) {
            // Отключаем проверку ключей, чтобы удаление прошло без ошибок Foreign Key
            QUERY_RUNNER.execute(connection, "SET FOREIGN_KEY_CHECKS = 0");
            QUERY_RUNNER.execute(connection, "TRUNCATE auth_codes");
            QUERY_RUNNER.execute(connection, "TRUNCATE card_transactions");
            QUERY_RUNNER.execute(connection, "TRUNCATE cards");
            QUERY_RUNNER.execute(connection, "TRUNCATE users");
            QUERY_RUNNER.execute(connection, "SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        try (var connection = getConn()) {
            QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
        }
    }
}
