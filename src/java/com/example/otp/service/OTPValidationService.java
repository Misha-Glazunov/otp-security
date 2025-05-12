import java.sql.*;

public class OTPValidationService {
    private final Connection connection;

    public OTPValidationService(Connection connection) {
        this.connection = connection;
    }

    public boolean validateOTP(String otp, String operationId) throws SQLException {
        String query = "SELECT status FROM otp_codes WHERE otp = ? AND operation_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, otp);
            ps.setString(2, operationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                return "ACTIVE".equals(status); // Если статус ACTIVE, код валиден
            }
        }
        return false;
    }
}