package dal;

import data.Feature;
import java.sql.*;
import java.util.*;
import data.LeaveRequest;
import data.LeaveStatus;
import data.Role;
import data.User;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeaveRequestDBContext extends DBContext<LeaveRequest> {
    
    public boolean hasOverlappingLeaveRequest(int userId, java.sql.Date fromDate, java.sql.Date toDate, Integer excludeRequestId) {
        String sql = "SELECT COUNT(*) " +
                    "FROM LeaveRequest " +
                    "WHERE UserID = ? " +
                    "AND (FromDate <= ? AND ToDate >= ? " +
                    "OR FromDate <= ? AND ToDate >= ? " +
                    "OR FromDate >= ? AND ToDate <= ?)";
        if (excludeRequestId != null) {
            sql += " AND RequestID != ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, toDate);
            stmt.setDate(3, fromDate);
            stmt.setDate(4, toDate);
            stmt.setDate(5, fromDate);
            stmt.setDate(6, fromDate);
            stmt.setDate(7, toDate);
            if (excludeRequestId != null) {
                stmt.setInt(8, excludeRequestId);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi kiểm tra trùng lặp thời gian: " + e.getMessage(), e);
            throw new RuntimeException("Lỗi khi kiểm tra trùng lặp thời gian: " + e.getMessage(), e);
        }
        return false;
    }
    
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

            if (model.getApprovedBy() != null) {
                pstmt.setInt(6, model.getApprovedBy());
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }

            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(7, currentTimestamp);
            pstmt.setTimestamp(8, currentTimestamp);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi chèn LeaveRequest: " + e.getMessage(), e);
        }
    }

    public ArrayList<LeaveRequest> list(int userId) {
        ArrayList<LeaveRequest> requests = new ArrayList<>();
        if (connection == null) {
            throw new RuntimeException("Database connection is not initialized.");
        }
        String sql = """
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
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LeaveStatus leaveStatus = new LeaveStatus();
                leaveStatus.setStatusID(rs.getInt("StatusID"));
                leaveStatus.setStatusName(rs.getString("StatusName"));
                leaveStatus.setDescription(rs.getString("Description"));

                LeaveRequest lr = new LeaveRequest();
                lr.setRequestID(rs.getInt("RequestID"));
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
        }
        return requests;
    }

    public void delete(int requestID) {
        String sql = """
                     DELETE FROM [dbo].[LeaveRequest]
                     WHERE [RequestID] = ?
                     """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, requestID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa LeaveRequest: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(LeaveRequest model) {
        String sql = """
                 UPDATE [dbo].[LeaveRequest]
                 SET [UserID] = ?,
                     [FromDate] = ?,
                     [ToDate] = ?,
                     [Reason] = ?,
                     [StatusID] = ?,
                     [ApprovedBy] = ?,
                     [CreatedAt] = ?,
                     [UpdatedAt] = ?
                 WHERE [RequestID] = ?
                 """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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

            pstmt.setTimestamp(7, model.getCreateAt());
            pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(9, model.getRequestID());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Không tìm thấy đơn xin nghỉ với RequestID: " + model.getRequestID());
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
        ArrayList<LeaveRequest> requests = new ArrayList<>();
        Map<Integer, String> userFullNameMap = new HashMap<>();
        if (connection == null) {
            throw new RuntimeException("Database connection is not initialized.");
        }
        String sql = """
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
                 """;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                LeaveStatus leaveStatus = new LeaveStatus();
                leaveStatus.setStatusID(rs.getInt("StatusID"));
                leaveStatus.setStatusName(rs.getString("StatusName"));
                leaveStatus.setDescription(rs.getString("Description"));

                LeaveRequest lr = new LeaveRequest();
                lr.setRequestID(rs.getInt("RequestID"));
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
                userFullNameMap.put(rs.getInt("UserID"), rs.getString("FullName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return requests;
    }

    @Override
    public LeaveRequest get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LeaveRequest newget(int id) {
        if (connection == null) {
            throw new RuntimeException("Database connection is not initialized.");
        }
        String sql = """
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
                      WHERE lr.[RequestID] = ?
                     """;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                LeaveStatus leaveStatus = new LeaveStatus();
                leaveStatus.setStatusID(rs.getInt("StatusID"));
                leaveStatus.setStatusName(rs.getString("StatusName"));
                leaveStatus.setDescription(rs.getString("Description"));

                LeaveRequest lr = new LeaveRequest();
                lr.setRequestID(rs.getInt("RequestID"));
                lr.setUserID(rs.getInt("UserID"));
                lr.setFromDate(rs.getDate("FromDate"));
                lr.setToDate(rs.getDate("ToDate"));
                lr.setReason(rs.getString("Reason"));
                lr.setStatusID(rs.getInt("StatusID"));
                lr.setLeaveStatus(leaveStatus);
                lr.setApprovedBy(rs.getObject("ApprovedBy") != null ? rs.getInt("ApprovedBy") : null);
                lr.setCreateAt(rs.getTimestamp("CreatedAt"));
                lr.setUpdateAt(rs.getTimestamp("UpdatedAt"));

                return lr;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Map<Integer, String> getUserFullNameMap() {
        Map<Integer, String> userFullNameMap = new HashMap<>();
        String sql = """
                     SELECT UserID, FullName
                     FROM [AssignmentDB].[dbo].[User]
                     """;
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                userFullNameMap.put(rs.getInt("UserID"), rs.getString("FullName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LeaveRequestDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userFullNameMap;
    }
}