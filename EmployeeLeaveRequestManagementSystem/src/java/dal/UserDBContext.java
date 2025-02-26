package dal;
import data.User;
import java.util.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.util.digester.ArrayStack;

public class UserDBContext extends DBContext<User>{

    @Override
    public ArrayList<User> list() {
        ArrayList<User> user = new ArrayStack<>();
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
                           ,[IsActive] FROM [assignment].[dbo].[User]""";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                int UserID = rs.getInt("UserID");
                String UserName = rs.getString("UserName");
                String PasswordHash = rs.getString("PasswordHash");
                String FullName = rs.getString("FullName");
                String Email = rs.getString("Email");
                String PhoneNumber = rs.getString("PhoneNumber");
                int DepartmentID = rs.getInt("DepartmentID");
                LocalDateTime CreatedAt = rs.getTimestamp("CreateAt").toLocalDateTime();
                LocalDateTime UpdateAt = rs.getTimestamp("UpdateAt").toLocalDateTime();
                boolean IsActive = rs.getBoolean("IsActive");
                
                User u = new User();
                u.setUserID(UserID);
                u.setUserName(UserName);
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
