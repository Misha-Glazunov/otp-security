package com.otp.dao;

import java.sql.*;
import com.otp.model.OtpCode;
import com.otp.util.DbConnection;

public class OtpCodeDao {

    public boolean saveOtpCode(OtpCode otpCode) {
        String query = "INSERT INTO otp_codes (operation_id, code, status) VALUES (?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, otpCode.getOperationId());
            stmt.setString(2, otpCode.getCode());
            stmt.setString(3, otpCode.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateOtpCodeStatus(int id, String status) {
        String query = "UPDATE otp_codes SET status = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}