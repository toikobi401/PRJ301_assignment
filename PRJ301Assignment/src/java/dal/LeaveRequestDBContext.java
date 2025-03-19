package dal;

import java.sql.*;
import java.util.*;
import data.LeaveRequest;
import data.LeaveStatus;


public class LeaveRequestDBContext extends DBContext<LeaveRequest> {

    @Override
    public ArrayList<LeaveRequest> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Chưa triển khai
    }

    @Override
    public LeaveRequest get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Chưa triển khai
    }

    @Override
    public void insert(LeaveRequest model) {
        String sql = """
                     USE [AssignmentDB]
                     GO
                     
                     INSERT INTO [dbo].[LeaveRequest]
                                ([UserID]
                                ,[FromDate]
                                ,[ToDate]
                                ,[Reason]
                                ,[StatusID]
                                ,[ApprovedBy]
                                ,[CreatedAt]
                                ,[UpdatedAt])
                          VALUES
                                (?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?
                                ,?)
                     GO
                     
                     """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Gán các giá trị từ đối tượng LeaveRequest vào câu lệnh SQL
            pstmt.setInt(1, model.getUserID());
            pstmt.setDate(2, new java.sql.Date(model.getFromDate().getTime()));
            pstmt.setDate(3, new java.sql.Date(model.getToDate().getTime()));
            pstmt.setString(4, model.getReason());
            pstmt.setInt(5, model.getStatusID());
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(6, currentTimestamp); // Thời gian tạo
            pstmt.setTimestamp(7, currentTimestamp); // Thời gian cập nhật
            pstmt.executeUpdate(); // Thực thi truy vấn
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi chèn LeaveRequest: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(LeaveRequest model) {
        throw new UnsupportedOperationException("Not supported yet."); // Chưa triển khai
    }

    @Override
    public void delete(LeaveRequest model) {
        throw new UnsupportedOperationException("Not supported yet."); // Chưa triển khai
    }
}