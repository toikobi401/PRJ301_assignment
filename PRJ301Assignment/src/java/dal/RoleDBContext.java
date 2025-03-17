
package dal;

import data.Role;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RoleDBContext extends DBContext<Role>{

     
    
    
    @Override
    public ArrayList<Role> list() {
        ArrayList<Role> role = new ArrayList<>();
        
        if (connection == null) {
        throw new RuntimeException("Database connection is not initialized.");
    }
        String sql = """
                SELECT [RoleID]
                      ,[RoleName]
                      ,[Description]
                  FROM [AssignmentDB].[dbo].[Role]
                     """;
            try {
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
                int RoleID = rs.getInt("RoleID");
                String RoleName = rs.getString("RoleName");
                String Description = rs.getString("Description");
                Role r = new Role();
                r.setRoleID(RoleID);
                r.setRoleName(RoleName);
                r.setDescription(Description);
                role.add(r);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
            return role;
    }

    @Override
    public Role get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Role model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Role model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Role model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
