package dal;
import data.AuditLog;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDateTime;




public class AuditLogDBContext extends DBContext<AuditLog>{

    @Override
    public ArrayList<AuditLog> list() {
    ArrayList<AuditLog> auditlog = new ArrayList<>();
        
        if (connection == null) {
        throw new RuntimeException("Database connection is not initialized.");
    }
        String sql = """
                SELECT [LogID]
                      ,[UserID]
                      ,[Action]
                      ,[Entity]
                      ,[EntityID]
                      ,[Timestamp]
                  FROM [assignment].[dbo].[AuditLog]
                     """;
            try {
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
               int LogID = rs.getInt("LogID");
               int UserID = rs.getInt("UserID");
               String Action = rs.getString("Action");
               String Entity = rs.getString("Entity");
               int EntityID = rs.getInt("EntityID");
               LocalDateTime Timestamp = rs.getTimestamp("Timestamp").toLocalDateTime();
               
               AuditLog a = new AuditLog();
               
               a.setLogID(LogID);
               a.setUserID(UserID);
               a.setAction(Action);
               a.setEntity(Entity);
               a.setEntityID(EntityID);
               a.setTimestamp(Timestamp);
               
               auditlog.add(a);
               
                       
                     
               
               
        }
    } catch (SQLException ex) {
        Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
            return auditlog;    }

    @Override
    public AuditLog get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(AuditLog model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(AuditLog model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(AuditLog model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
