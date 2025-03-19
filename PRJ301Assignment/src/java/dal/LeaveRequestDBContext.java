package dal;

import java.sql.*;
import java.util.*;
import data.LeaveRequest;
import data.LeaveStatus;

import java.util.logging.Level;
import java.util.logging.Logger;
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
    
public ArrayList<LeaveRequest> list(int userId) {
        ArrayList<LeaveRequest> requests = new ArrayList<>();
        if (connection == null) {
            throw new RuntimeException("Database connection is not initialized.");
        }
        String sql = """
                     SELECT 
                           lr.[UserID]
                          ,lr.[FromDate]
                          ,lr.[ToDate]
                          ,lr.[Reason]
                          ,lr.[StatusID]
                          ,ls.[StatusName]
                          ,ls.[Description]
                          ,lr.[ApprovedBy]
                          ,lr.[CreatedAt]
                          ,lr.[UpdatedAt]
                      FROM [AssignmentDB].[dbo].[LeaveRequest] as lr
                      LEFT JOIN [AssignmentDB].[dbo].[LeaveStatus] as ls
                      ON ls.StatusID = lr.StatusID
                      WHERE lr.[UserID] = ?
                     """;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId); // Gán giá trị UserID vào tham số
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng LeaveStatus
                LeaveStatus leaveStatus = new LeaveStatus();
                leaveStatus.setStatusID(rs.getInt("StatusID"));
                leaveStatus.setStatusName(rs.getString("StatusName"));
                leaveStatus.setDescription(rs.getString("Description"));

                // Tạo đối tượng LeaveRequest
                LeaveRequest lr = new LeaveRequest();
                lr.setUserID(rs.getInt("UserID"));
                lr.setFromDate(rs.getDate("FromDate"));
                lr.setToDate(rs.getDate("ToDate"));
                lr.setReason(rs.getString("Reason"));
                lr.setStatusID(rs.getInt("StatusID"));
                lr.setLeaveStatus(leaveStatus);
                lr.setApprovedBy(rs.getObject("ApprovedBy") != null ? rs.getInt("ApprovedBy") : null);
                lr.setCreateAt(rs.getTimestamp("CreatedAt"));
                lr.setUpdateAt(rs.getTimestamp("UpdatedAt"));

                requests.add(lr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return requests;
    }

    @Override
    public LeaveRequest get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(LeaveRequest model) {
        String sql = """
                     UPDATE [dbo].[LeaveRequest]
                        SET [UserID] = ?
                           ,[FromDate] = ?
                           ,[ToDate] = ?
                           ,[Reason] = ?
                           ,[StatusID] = ?
                           ,[ApprovedBy] = ?
                           ,[CreatedAt] = ?
                           ,[UpdatedAt] = ?
                      WHERE [UserID] = ?
                     """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Gán giá trị cho các cột cần cập nhật
            pstmt.setInt(1, model.getUserID());
            pstmt.setDate(2, new java.sql.Date(model.getFromDate().getTime()));
            pstmt.setDate(3, new java.sql.Date(model.getToDate().getTime()));
            pstmt.setString(4, model.getReason());
            pstmt.setInt(5, model.getStatusID());
            if (model.getApprovedBy() != null) {
                pstmt.setInt(6, model.getApprovedBy());
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }
            pstmt.setTimestamp(7, model.getCreateAt()); // Giữ nguyên CreatedAt từ model
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(8, currentTimestamp); // Cập nhật UpdatedAt thành thời gian hiện tại
            pstmt.setInt(9, model.getUserID()); // Điều kiện WHERE dựa trên UserID

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Không tìm thấy bản ghi với UserID = " + model.getUserID() + " để cập nhật.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật LeaveRequest: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(LeaveRequest model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<LeaveRequest> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}