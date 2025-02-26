
package dal;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class DBContext<T> {
    protected Connection connection;
    public DBContext()
    {
        try {
        String user = "dat";
        String pass = "1234";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=assignment;encrypt=true;trustServerCertificate=true";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, user, pass);
        System.out.println("Database connected successfully!");
    } catch (ClassNotFoundException ex) {
        System.err.println("JDBC Driver not found: " + ex.getMessage());
    } catch (SQLException ex) {
        System.err.println("Database connection failed: " + ex.getMessage());
    }
    
    }
    
    public abstract ArrayList<T> list();
    public abstract T get(int id);
    public abstract void insert(T model);
    public abstract void update(T model);
    public abstract void delete(T model);
}
