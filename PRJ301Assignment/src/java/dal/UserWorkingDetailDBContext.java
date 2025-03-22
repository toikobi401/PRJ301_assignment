package dal;

import data.LeaveRequest;
import data.LeaveStatus;
import data.User;
import data.UserWorkingDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserWorkingDetailDBContext extends DBContext<UserWorkingDetail> {

    // Lấy thông tin user và danh sách đơn xin nghỉ của họ dựa trên userId
    public UserWorkingDetail getUserWorkingDetail(int userId) {
        User user = null;
        ArrayList<LeaveRequest> leaveRequests = new ArrayList<>();

        if (connection == null) {
            throw new RuntimeException("Database connection is not initialized.");
        }

        // Truy vấn thông tin user
        String userSql = """
                        SELECT [UserID]
                              ,[Username]
                              ,[PasswordHash]
                              ,[FullName]
                              ,[DepartmentID]
                              ,[CreatedAt]
                              ,[UpdatedAt]
                              ,[IsActive]
                          FROM [AssignmentDB].[dbo].[User]
                          WHERE [UserID] = ?
                        """;
        try {
            PreparedStatement userStmt = connection.prepareStatement(userSql);
            userStmt.setInt(1, userId);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                user = new User();
                user.setUserID(userRs.getInt("UserID"));
                user.setUsername(userRs.getString("Username"));
                user.setPasswordHash(userRs.getString("PasswordHash"));
                user.setFullName(userRs.getString("FullName"));
                user.setDepartmentID(userRs.getInt("DepartmentID"));
                user.setCreatedAt(userRs.getTimestamp("CreatedAt"));
                user.setUpdateAt(userRs.getTimestamp("UpdatedAt"));
                user.setIsActive(userRs.getBoolean("IsActive"));
            } else {
                return null; // Không tìm thấy user
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserWorkingDetailDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy thông tin user: " + ex.getMessage(), ex);
            return null;
        }

        // Truy vấn danh sách đơn xin nghỉ của user
        String leaveSql = """
                         SELECT 
                               lr.[RequestID],
                               lr.[UserID],
                               u.[FullName],
                               lr.[FromDate],
                               lr.[ToDate],
                               lr.[Reason],
                               lr.[StatusID],
                               ls.[StatusName],
                               ls.[Description],
                               lr.[ApprovedBy],
                               lr.[CreatedAt],
                               lr.[UpdatedAt]
                          FROM [AssignmentDB].[dbo].[LeaveRequest] as lr
                          LEFT JOIN [AssignmentDB].[dbo].[LeaveStatus] as ls
                          ON ls.StatusID = lr.StatusID
                          LEFT JOIN [AssignmentDB].[dbo].[User] as u
                          ON lr.UserID = u.UserID
                          WHERE lr.[UserID] = ?
                         """;
        try {
            PreparedStatement leaveStmt = connection.prepareStatement(leaveSql);
            leaveStmt.setInt(1, userId);
            ResultSet leaveRs = leaveStmt.executeQuery();

            while (leaveRs.next()) {
                LeaveStatus leaveStatus = new LeaveStatus();
                leaveStatus.setStatusID(leaveRs.getInt("StatusID"));
                leaveStatus.setStatusName(leaveRs.getString("StatusName"));
                leaveStatus.setDescription(leaveRs.getString("Description"));

                LeaveRequest lr = new LeaveRequest();
                lr.setRequestID(leaveRs.getInt("RequestID"));
                lr.setUserID(leaveRs.getInt("UserID"));
                lr.setFromDate(leaveRs.getDate("FromDate"));
                lr.setToDate(leaveRs.getDate("ToDate"));
                lr.setReason(leaveRs.getString("Reason"));
                lr.setStatusID(leaveRs.getInt("StatusID"));
                lr.setLeaveStatus(leaveStatus);
                lr.setApprovedBy(leaveRs.getObject("ApprovedBy") != null ? leaveRs.getInt("ApprovedBy") : null);
                lr.setCreateAt(leaveRs.getTimestamp("CreatedAt"));
                lr.setUpdateAt(leaveRs.getTimestamp("UpdatedAt"));

                leaveRequests.add(lr);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserWorkingDetailDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách đơn xin nghỉ: " + ex.getMessage(), ex);
        }

        // Tạo đối tượng UserWorkingDetail và trả về
        return new UserWorkingDetail(user, leaveRequests);
    }

    @Override
    public ArrayList<UserWorkingDetail> list() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UserWorkingDetail get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(UserWorkingDetail model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(UserWorkingDetail model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(UserWorkingDetail model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}