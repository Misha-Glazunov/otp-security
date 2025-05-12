import java.sql.*;

public class UserService {
    private final Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
    }

    public boolean registerUser(String username, String password, String role) throws SQLException {
        // Проверим, существует ли уже пользователь
        String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(checkUserQuery)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                return false; // Пользователь уже существует
            }
        }

        // Регистрация нового пользователя
        String insertUserQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertUserQuery)) {
            ps.setString(1, username);
            ps.setString(2, password); // Здесь нужно хешировать пароль
            ps.setString(3, role);
            ps.executeUpdate();
        }

        return true;
    }
}