package org.qz.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public final class SQLiteTestDataStore {

    private static final String DB_PATH_PROPERTY = "testdata.db.path";
    private static final String DEFAULT_DB_RELATIVE_PATH = "/src/test/resources/testdata/test_data.db";

    private static Connection connection;

    private SQLiteTestDataStore() {
    }

    public static synchronized void initialize() {
        if (connection != null) {
            return;
        }

        try {
            String dbPath = resolveDbPath();
            ensureParentDirectoryExists(dbPath);
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            createTables();
            seedDefaults();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to initialize SQLite test data store", exception);
        }
    }

    public static synchronized void close() {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to close SQLite test data store", exception);
        } finally {
            connection = null;
        }
    }

    public static Map<String, String> getCredentials(String dataKey) {
        return getByKey(
            "SELECT username, password FROM credentials WHERE data_key = ?",
            dataKey,
            "username",
            "password"
        );
    }

    public static void saveCredentials(String dataKey, String username, String password) {
        executeUpsert(
            "INSERT INTO credentials(data_key, username, password) VALUES(?, ?, ?) " +
                "ON CONFLICT(data_key) DO UPDATE SET username = excluded.username, password = excluded.password",
            dataKey,
            username,
            password
        );
    }

    public static Map<String, String> getContact(String dataKey) {
        return getByKey(
            "SELECT first_name, last_name, company, identity, email FROM contacts WHERE data_key = ?",
            dataKey,
            "first_name",
            "last_name",
            "company",
            "identity",
            "email"
        );
    }

    public static void saveContact(String dataKey, String firstName, String lastName, String company, String identity, String email) {
        executeUpsert(
            "INSERT INTO contacts(data_key, first_name, last_name, company, identity, email) VALUES(?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT(data_key) DO UPDATE SET first_name = excluded.first_name, last_name = excluded.last_name, " +
                "company = excluded.company, identity = excluded.identity, email = excluded.email",
            dataKey,
            firstName,
            lastName,
            company,
            identity,
            email
        );
    }

    public static Map<String, String> getCheckoutData(String dataKey) {
        return getByKey(
            "SELECT first_name, last_name, pin, order_message FROM checkout_data WHERE data_key = ?",
            dataKey,
            "first_name",
            "last_name",
            "pin",
            "order_message"
        );
    }

    public static void saveCheckoutData(String dataKey, String firstName, String lastName, String pin, String orderMessage) {
        executeUpsert(
            "INSERT INTO checkout_data(data_key, first_name, last_name, pin, order_message) VALUES(?, ?, ?, ?, ?) " +
                "ON CONFLICT(data_key) DO UPDATE SET first_name = excluded.first_name, last_name = excluded.last_name, " +
                "pin = excluded.pin, order_message = excluded.order_message",
            dataKey,
            firstName,
            lastName,
            pin,
            orderMessage
        );
    }

    private static Map<String, String> getByKey(String sql, String dataKey, String... columns) {
        initialize();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, dataKey);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new IllegalArgumentException("No test data found for key: " + dataKey);
                }

                Map<String, String> values = new HashMap<>();
                for (String column : columns) {
                    values.put(toCamelCase(column), resultSet.getString(column));
                }
                return values;
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to fetch test data for key: " + dataKey, exception);
        }
    }

    private static void executeUpsert(String sql, String... values) {
        initialize();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                statement.setString(i + 1, values[i]);
            }
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new RuntimeException("Unable to save test data", exception);
        }
    }

    private static void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(
                "CREATE TABLE IF NOT EXISTS credentials(" +
                    "data_key TEXT PRIMARY KEY, " +
                    "username TEXT NOT NULL, " +
                    "password TEXT NOT NULL)"
            );
            statement.execute(
                "CREATE TABLE IF NOT EXISTS contacts(" +
                    "data_key TEXT PRIMARY KEY, " +
                    "first_name TEXT NOT NULL, " +
                    "last_name TEXT NOT NULL, " +
                    "company TEXT, " +
                    "identity TEXT, " +
                    "email TEXT)"
            );
            statement.execute(
                "CREATE TABLE IF NOT EXISTS checkout_data(" +
                    "data_key TEXT PRIMARY KEY, " +
                    "first_name TEXT NOT NULL, " +
                    "last_name TEXT NOT NULL, " +
                    "pin TEXT NOT NULL, " +
                    "order_message TEXT NOT NULL)"
            );
        }
    }

    private static void seedDefaults() {
        saveCredentials("default_login", "standard_user", "secret_sauce");
        saveCredentials("cogmento_login", "siva8kolli@gmail.com", "Selenium@123");
        saveContact("default_contact", "Ravi", "Sindri", "QualiZeal", "Brown Eyes", "ravi@yopmail.com");
        saveCheckoutData("default_checkout", "John", "Doe", "56001", "Thnaks for your order");
    }

    private static String resolveDbPath() {
        String dbPathFromProperty = System.getProperty(DB_PATH_PROPERTY);
        if (dbPathFromProperty != null && !dbPathFromProperty.isBlank()) {
            return dbPathFromProperty;
        }
        return System.getProperty("user.dir") + DEFAULT_DB_RELATIVE_PATH;
    }

    private static void ensureParentDirectoryExists(String dbPath) {
        File databaseFile = new File(dbPath);
        File parent = databaseFile.getParentFile();
        if (parent == null || parent.exists()) {
            return;
        }

        if (!parent.mkdirs()) {
            throw new RuntimeException("Unable to create test data directory: " + parent.getAbsolutePath());
        }
    }

    private static String toCamelCase(String value) {
        String[] parts = value.split("_");
        StringBuilder result = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            result.append(parts[i].substring(0, 1).toUpperCase())
                .append(parts[i].substring(1));
        }
        return result.toString();
    }
}
