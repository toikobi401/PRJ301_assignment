package data;

import java.sql.*;

public class LeaveRequest {
    private int RequestID;
    private int UserID;
    private java.sql.Date FromDate;
    private java.sql.Date ToDate;
    private String Reason;
    private int StatusID;
    private Integer ApprovedBy; // Thay đổi từ int sang Integer
    private java.sql.Timestamp CreateAt;
    private java.sql.Timestamp UpdateAt;

    public int getRequestID() {
        return RequestID;
    }

    public void setRequestID(int RequestID) {
        this.RequestID = RequestID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public Date getFromDate() {
        return FromDate;
    }

    public void setFromDate(Date FromDate) {
        this.FromDate = FromDate;
    }

    public Date getToDate() {
        return ToDate;
    }

    public void setToDate(Date ToDate) {
        this.ToDate = ToDate;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public int getStatusID() {
        return StatusID;
    }

    public void setStatusID(int StatusID) {
        this.StatusID = StatusID;
    }

    public Integer getApprovedBy() { // Thay đổi từ int sang Integer
        return ApprovedBy;
    }

    public void setApprovedBy(Integer ApprovedBy) { // Thay đổi từ int sang Integer
        this.ApprovedBy = ApprovedBy;
    }

    public Timestamp getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(Timestamp CreateAt) {
        this.CreateAt = CreateAt;
    }

    public Timestamp getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(Timestamp UpdateAt) {
        this.UpdateAt = UpdateAt;
    }
}