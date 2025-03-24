
package dal;
import data.Department;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class DepartmentDBContext extends DBContext<Department>{

    @Override
    public ArrayList<Department> list() {
        ArrayList<Department> dep = new ArrayList<>();
        
        if (connection == null) {
        throw new RuntimeException("Database connection is not initialized.");
    }
        String sql = """
                SELECT  [DepartmentID]
                      ,[DepartmentName]
                      ,[ManagerID]
                  FROM [AssignmentDB].[dbo].[Department]
                     """;
            try {
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
                int DepartmentID = rs.getInt("DepartmentID");
                String DepartmentName = rs.getString("DepartmentName"); // Missing 't' in "DepartmentName"
                int ManagerID = rs.getInt("ManagerID");
                
                Department d = new Department();
               
                d.setDepartmentID(DepartmentID);
                d.setDepartmentName(DepartmentName);
                d.setManagerID(ManagerID);
                
                dep.add(d);
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
            return dep;    
    }

    @Override
    public Department get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Department model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Department model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Department model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
