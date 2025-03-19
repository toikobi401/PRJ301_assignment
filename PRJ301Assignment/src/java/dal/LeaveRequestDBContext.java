package dal;

import java.sql.*;
import java.util.*;
import data.LeaveRequest;

public class LeaveRequestDBContext extends DBContext<LeaveRequest> {

    @Override
    public void insert(LeaveRequest model) {
        String sql = """
                    
                     
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
                     
                     """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, model.getUserID());
            pstmt.setDate(2, new java.sql.Date(model.getFromDate().getTime()));
            pstmt.setDate(3, new java.sql.Date(model.getToDate().getTime()));
            pstmt.setString(4, model.getReason());
            pstmt.setInt(5, model.getStatusID());
            
            // Xử lý ApprovedBy có thể là null
            if (model.getApprovedBy() != null) {
                pstmt.setInt(6, model.getApprovedBy());
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }
            
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(7, currentTimestamp); // Thời gian tạo
            pstmt.setTimestamp(8, currentTimestamp); // Thời gian cập nhật
            pstmt.executeUpdate(); // Thực thi truy vấn
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi chèn LeaveRequest: " + e.getMessage(), e);
        }
    }

    // Các phương thức khác giữ nguyên
    @Override
    public ArrayList<LeaveRequest> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public LeaveRequest get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(LeaveRequest model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(LeaveRequest model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}