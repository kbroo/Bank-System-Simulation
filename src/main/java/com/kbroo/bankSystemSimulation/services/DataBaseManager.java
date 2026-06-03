package com.kbroo.bankSystemSimulation.services;

import com.kbroo.bankSystemSimulation.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataBaseManager {
    private final String URL = "jdbc:h2:./bank_db";
    private final String user = "root";
    private final String password = "";
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, user, password);
            System.out.println("Соединение с бд открыто.");
        }
        return connection;
    }

    public void close() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Соединение с бд закрыто.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии соединения: " + e.getMessage());
        }
    }

    public void initDB() {
        String createClients = """
            CREATE TABLE IF NOT EXISTS clients (
                id VARCHAR(36) PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL,
                credit_rating INT DEFAULT 5
            )
            """;

        String createAccounts = """
            CREATE TABLE IF NOT EXISTS accounts (
                account_number VARCHAR(20) PRIMARY KEY,
                client_id VARCHAR(36) NOT NULL,
                account_type VARCHAR(20) NOT NULL,
                balance DECIMAL(15,2) DEFAULT 0,
                opened_at DATE DEFAULT CURRENT_DATE,
                credit_limit DECIMAL(15,2) DEFAULT 0,
                FOREIGN KEY (client_id) REFERENCES clients(id)
            )
            """;

        String createTransactions = """
            CREATE TABLE IF NOT EXISTS transactions (
                id VARCHAR(36) PRIMARY KEY,
                from_account VARCHAR(20) NOT NULL,
                to_account VARCHAR(20) NOT NULL,
                amount DECIMAL(15,2) NOT NULL,
                description VARCHAR(255),
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createClients);
            stmt.execute(createAccounts);
            stmt.execute(createTransactions);
            System.out.println("Успешная инициализация таблиц БД.");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании инициализации БД: " + e.getMessage());
        }
    }

    public void saveClient(Client client) {
        String sql = "INSERT INTO clients (id, name, email, credit_rating) VALUES (?, ?, ?, ?)";
        try (Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getId().toString());
            stmt.setString(2, client.getUsername());
            stmt.setString(3, client.getEmail());
            stmt.setInt(4, client.getCreditRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении пользователя: " + e.getMessage());
        }
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(extractClient(rs));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении пользователей: " + e.getMessage());
        }
        return clients;
    }

    public Optional<Client> getClientById(String id) {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractClient(rs));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске пользователя по id: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void saveTransfer(Transaction transaction) {
        String sql = "INSERT INTO transactions (id, from_account, to_account, amount, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transaction.getId().toString());
            stmt.setString(2, transaction.getFrom());
            stmt.setString(3, transaction.getTo());
            stmt.setBigDecimal(4, transaction.getAmount());
            stmt.setString(5, transaction.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении транзакции: " + e.getMessage());
        }
    }

    private Client extractClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        client.setID(rs.getString("id"));
        client.setUsername(rs.getString("name"));
        client.setEmail(rs.getString("email"));
        client.setCreditRating(rs.getInt("credit_rating"));
        return client;
    }

    public void saveAccount(Account account) {
        String sql = "INSERT INTO accounts (account_number, client_id, account_type, balance, opened_at, credit_limit) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getAccountNumber());
            stmt.setString(2, account.getOwner().getId().toString());
            stmt.setString(3, account.getAccountType().name());
            stmt.setBigDecimal(4, account.getBalance());
            stmt.setDate(5, Date.valueOf(account.getOpenedAt()));
            if (account instanceof CreditAccount credit) {
                stmt.setBigDecimal(6, credit.getCreditLimit());
            } else {
                stmt.setBigDecimal(6, BigDecimal.ZERO);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении счета: " + e.getMessage());
        }
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        try (Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                extractAccount(rs).ifPresent(acc -> accounts.add(acc));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении счетов: " + e.getMessage());
        }
        return accounts;
    }

    public void updateAccountBalance(String accountNumber, BigDecimal amount) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, amount);
            stmt.setString(2, accountNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении баланса: " + e.getMessage());
        }
    }

    private Optional<Account> extractAccount(ResultSet rs) throws SQLException {
        String number = rs.getString("account_number");
        String client_id = rs.getString("client_id");
        String type = rs.getString("account_type");
        BigDecimal balance = rs.getBigDecimal("balance");
        LocalDate openedAt = rs.getDate("opened_at").toLocalDate();
        Client owner = this.getClientById(client_id).orElse(null);
        if (owner == null) return Optional.empty();

        Account account = switch (type) {
            case "DEBIT" -> new DebitAccount(number, owner);
            case "SAVINGS" -> new SavingsAccount(number, owner);
            case "CREDIT" -> new CreditAccount(number, owner);
            default -> null;
        };

        if (account != null) {
            account.setBalance(balance);
            account.setOpenedAt(openedAt);
            return Optional.of(account);
        }
        return Optional.empty();
    }
}
