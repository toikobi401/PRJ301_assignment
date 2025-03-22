package dal;

import data.*;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDBContext extends DBContext<User> {
    
    // Tìm kiếm người dùng theo tên (fullName chứa từ khóa)
    public ArrayList<User> searchByName(String search) {
        ArrayList<User> users = new ArrayList<>();
        if (connection == null) {
            throw new RuntimeException("Database connection is not initialized.");
        }
        String sql = """
                SELECT [UserID]
                      ,[Username]
                      ,[PasswordHash]
                      ,[FullName]
                      ,[DepartmentID]
                      ,[CreatedAt]
                      ,[UpdatedAt]
                      ,[IsActive]
                  FROM [AssignmentDB].[dbo].[User]
                  WHERE [FullName] LIKE ?
                 """;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + search + "%"); // Tìm kiếm không phân biệt hoa thường
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setFullName(rs.getString("FullName"));
                u.setDepartmentID(rs.getInt("DepartmentID"));
                u.setCreatedAt(rs.getTimestamp("CreatedAt"));
                u.setUpdateAt(rs.getTimestamp("UpdatedAt"));
                u.setIsActive(rs.getBoolean("IsActive"));
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi tìm kiếm user: " + ex.getMessage(), ex);
        }
        return users;
    }
    
    public User get(String username, String password) {
        User user = null;
        if (connection == null) {
            throw new RuntimeException("Database connection is not initialized.");
        }
        String sql = """
                    SELECT  u.[UserID]
                           ,[Username]
                           ,[PasswordHash]
                           ,[FullName]
                           ,[DepartmentID]
                           ,[CreatedAt]
                           ,[UpdatedAt]
                           ,[IsActive]
                           ,r.RoleID
                           ,r.RoleName
                           ,f.FeatureID
                           ,f.FeatureURL
                     FROM [AssignmentDB].[dbo].[User] as u
                     LEFT JOIN UserRole as ur on ur.UserID = u.UserID
                     LEFT JOIN Role as r on ur.RoleID = r.RoleID
                     LEFT JOIN FeatureRole as fr on fr.RoleID = r.RoleID
                     LEFT JOIN Feature as f on f.FeatureID = fr.FeatureID
                     WHERE [Username] = ? and PasswordHash = ?
                 """;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();

            Role current_role = new Role();
            current_role.setRoleID(-1);
            while (rs.next()) {
                if (user == null) {
                    user = new User();
                    user.setUserID(rs.getInt("UserID"));
                    user.setUsername(username);
                    user.setFullName(rs.getString("FullName"));
                    user.setPasswordHash(password);
                    user.setDepartmentID(rs.getInt("DepartmentID"));
                    user.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    user.setUpdateAt(rs.getTimestamp("UpdatedAt"));
                    user.setIsActive(rs.getBoolean("IsActive"));
                }
                int roleId = rs.getInt("RoleID");
                if (roleId != 0 && roleId != current_role.getRoleID()) {
                    current_role = new Role();
                    current_role.setRoleID(roleId);
                    current_role.setRoleName(rs.getString("RoleName"));
                    user.getRoles().add(current_role);
                    current_role.getUsers().add(user);
                }
                int featureId = rs.getInt("FeatureID");
                if (featureId != 0) {
                    Feature f = new Feature();
                    f.setFeatureID(featureId);
                    f.setFeatureURL(rs.getString("FeatureURL"));
                    current_role.getFeatures().add(f);
                    f.getRoles().add(current_role);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy user: " + ex.getMessage(), ex);
        }
        return user;
    }

    @Override
    public ArrayList<User> list() {
        ArrayList<User> users = new ArrayList<>();
        if (connection == null) {
            throw new RuntimeException("Database connection is not initialized.");
        }
        String sql = """
                SELECT [UserID]
                      ,[Username]
                      ,[PasswordHash]
                      ,[FullName]
                      ,[DepartmentID]
                      ,[CreatedAt]
                      ,[UpdatedAt]
                      ,[IsActive]
                  FROM [AssignmentDB].[dbo].[User]
                 """;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getInt("UserID"));
                u.setUsername(rs.getString("Username"));
                u.setPasswordHash(rs.getString("PasswordHash"));
                u.setFullName(rs.getString("FullName"));
                u.setDepartmentID(rs.getInt("DepartmentID"));
                u.setCreatedAt(rs.getTimestamp("CreatedAt"));
                u.setUpdateAt(rs.getTimestamp("UpdatedAt"));
                u.setIsActive(rs.getBoolean("IsActive"));
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách user: " + ex.getMessage(), ex);
        }
        return users;
    }

    public User newget(int id) {
        User user = null;
        if (connection == null) {
            throw new RuntimeException("Database connection is not initialized.");
        }
        String sql = """
                 SELECT  u.[UserID]
                        ,[Username]
                        ,[PasswordHash]
                        ,[FullName]
                        ,[DepartmentID]
                        ,[CreatedAt]
                        ,[UpdatedAt]
                        ,[IsActive]
                        ,r.RoleID
                        ,r.RoleName
                        ,f.FeatureID
                        ,f.FeatureURL
                 FROM [AssignmentDB].[dbo].[User] as u
                 LEFT JOIN UserRole as ur on ur.UserID = u.UserID
                 LEFT JOIN Role as r on ur.RoleID = r.RoleID
                 LEFT JOIN FeatureRole as fr on fr.RoleID = r.RoleID
                 LEFT JOIN Feature as f on f.FeatureID = fr.FeatureID
                 WHERE u.[UserID] = ?
                 """;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            Role current_role = new Role();
            current_role.setRoleID(-1);
            while (rs.next()) {
                if (user == null) {
                    user = new User();
                    user.setUserID(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setFullName(rs.getString("FullName"));
                    user.setPasswordHash(rs.getString("PasswordHash"));
                    user.setDepartmentID(rs.getInt("DepartmentID"));
                    user.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    user.setUpdateAt(rs.getTimestamp("UpdatedAt"));
                    user.setIsActive(rs.getBoolean("IsActive"));
                }
                int roleId = rs.getInt("RoleID");
                if (roleId != 0 && roleId != current_role.getRoleID()) {
                    current_role = new Role();
                    current_role.setRoleID(roleId);
                    current_role.setRoleName(rs.getString("RoleName"));
                    user.getRoles().add(current_role);
                    current_role.getUsers().add(user);
                }
                int featureId = rs.getInt("FeatureID");
                if (featureId != 0) {
                    Feature f = new Feature();
                    f.setFeatureID(featureId);
                    f.setFeatureURL(rs.getString("FeatureURL"));
                    current_role.getFeatures().add(f);
                    f.getRoles().add(current_role);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, "Lỗi khi lấy user: " + ex.getMessage(), ex);
        }
        return user;
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}