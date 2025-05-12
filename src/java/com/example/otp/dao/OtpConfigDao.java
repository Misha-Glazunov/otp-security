package com.otp.dao;

import java.sql.*;
import com.otp.model.OtpConfig;
import com.otp.util.DbConnection;

public class OtpConfigDao {

    public Optional<OtpConfig> getConfig() {
        String query = "SELECT * FROM otp_config LIMIT 1";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                OtpConfig config = new OtpConfig(
                        rs.getInt("code_length"),
                        rs.getInt("expiration_time")
                );
                return Optional.of(config);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean updateConfig(OtpConfig config) {
        String query = "UPDATE otp_config SET code_length = ?, expiration_time = ? WHERE id = 1";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, config.getCodeLength());
            stmt.setInt(2, config.getExpirationTime());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}