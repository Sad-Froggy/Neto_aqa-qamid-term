package ru.netology.utils;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class SQLHelper {
    public static String url = System.getProperty("db.url");
    public static String user = System.getProperty("db.user");
    public static String pass = System.getProperty("db.password");
    public static String statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC";
    private static final QueryRunner runner = new QueryRunner();

    @SneakyThrows
    public static void clearDB() {
        try (
                var connection = DriverManager.getConnection(url, user, pass)
        ) {
            runner.update(connection, "DELETE FROM credit_request_entity;");
            runner.update(connection, "DELETE FROM order_entity;");
            runner.update(connection, "DELETE FROM payment_entity;");
        }
    }

    @SneakyThrows
    public static String getStatus() {
        try (
                var connection = DriverManager.getConnection(url, user, pass)
        ) {
            return runner.query(connection, statusSQL, new ScalarHandler<>()).toString();
        }

    }

}
