package dal;
import data.User;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.util.digester.ArrayStack;

public class UserDBContext extends DBContext<User>{

public User get(String username, String password) {
    if (connection == null) {
        throw new RuntimeException("Database connection is not initialized.");
    }
    String sql = """
                SELECT [UserID]
                      ,[Username]
                      ,[PasswordHash]
                      ,[FullName]
                      ,[Email]
                      ,[PhoneNumber]
                      ,[DepartmentID]
                      ,[CreatedAt]
                      ,[UpdatedAt]
                      ,[IsActive]
                  FROM [AssignmentDB].[dbo].[User]
                  WHERE Username = ? AND PasswordHash = ?
                """;
    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1, username);
        stm.setString(2, password);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) { // Chỉ lấy user đầu tiên (nếu có)
            User u = new User();
            u.setUserID(rs.getInt("UserID"));
            u.setUsername(rs.getString("Username")); // Sửa "UserName" thành "Username"
            u.setPasswordHash(rs.getString("PasswordHash"));
            u.setFullName(rs.getString("FullName"));
            u.setEmail(rs.getString("Email"));
            u.setPhoneNumber(rs.getString("PhoneNumber"));
            u.setDepartmentID(rs.getInt("DepartmentID"));
            u.setCreatedAt(rs.getTimestamp("CreatedAt"));
            u.setUpdateAt(rs.getTimestamp("UpdatedAt"));
            u.setIsActive(rs.getBoolean("IsActive"));
            return u;
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null; // Trả về null nếu không tìm thấy user
}
    
    
@Override
public ArrayList<User> list() {
    ArrayList<User> user = new ArrayStack<>();
    if (connection == null) {
        throw new RuntimeException("Database connection is not initialized.");
    }
    String sql = """
                SELECT [UserID]
                             ,[Username]
                             ,[PasswordHash]
                             ,[FullName]
                             ,[Email]
                             ,[PhoneNumber]
                             ,[DepartmentID]
                             ,[CreatedAt]
                             ,[UpdatedAt]
                             ,[IsActive]
                         FROM [AssignmentDB].[dbo].[User]""";
    try {
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            // Populate User object...
                int UserID = rs.getInt("UserID");
                String UserName = rs.getString("UserName");
                String PasswordHash = rs.getString("PasswordHash");
                String FullName = rs.getString("FullName");
                String Email = rs.getString("Email");
                String PhoneNumber = rs.getString("PhoneNumber");
                int DepartmentID = rs.getInt("DepartmentID");
                Timestamp CreatedAt = rs.getTimestamp("CreatedAt");
                Timestamp UpdateAt = rs.getTimestamp("UpdatedAt");
                boolean IsActive = rs.getBoolean("IsActive");
                
                User u = new User();
                u.setUserID(UserID);
                u.setUsername(UserName);
                u.setPasswordHash(PasswordHash);
                u.setFullName(FullName);
                u.setEmail(Email);
                u.setPhoneNumber(PhoneNumber);
                u.setDepartmentID(DepartmentID);
                u.setCreatedAt(CreatedAt);
                u.setUpdateAt(UpdateAt);
                u.setIsActive(IsActive);
                user.add(u);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return user;
}

    @Override
    public User get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(User model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
