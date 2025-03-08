package dal;
import data.UserRole;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserRoleDBContext extends DBContext<UserRole>{

    @Override
    public ArrayList<UserRole> list() {
        ArrayList<UserRole> userrole = new ArrayList<>();
        
        if (connection == null) {
        throw new RuntimeException("Database connection is not initialized.");
    }
        String sql = """
                SELECT [UserRoleID]
                        ,[UserID]
                        ,[RoleID]
                    FROM [AssignmentDB].[dbo].[UserRole]
                     """;
            try {
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
                int UserRoleID = rs.getInt("UserRoleID");
                int RoleID = rs.getInt("RoleID");
                int UserID = rs.getInt("UserID");
                
                
                UserRole ur = new UserRole();
                ur.setRoleID(RoleID);
                ur.setUserID(UserID);
                ur.setUserRoleID(UserRoleID);
                userrole.add(ur);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
            return userrole;    
    }

    @Override
    public UserRole get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(UserRole model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(UserRole model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(UserRole model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
