package data;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;


public class User implements Serializable{
    private int UserID;
    private String Username;
    private String PasswordHash;
    private String FullName;
    private String Email;
    private String PhoneNumber;
    private int DepartmentID;
    private java.sql.Timestamp CreatedAt;
    private java.sql.Timestamp UpdateAt;
    private boolean IsActive;
    private ArrayList<Role> roles = new ArrayList<>();  

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Timestamp CreatedAt) {
        this.CreatedAt = CreatedAt;
    }

    public Timestamp getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(Timestamp UpdateAt) {
        this.UpdateAt = UpdateAt;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
    
    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String UserName) {
        this.Username = UserName;
    }

    public String getPasswordHash() {
        return PasswordHash;
    }

    public void setPasswordHash(String PasswordHash) {
        this.PasswordHash = PasswordHash;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int DepartmentID) {
        this.DepartmentID = DepartmentID;
    }

   

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }

    public void setUpdatedAt(Timestamp timestamp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    
}

