package data;

import java.time.LocalDateTime;

public class LeaveRequest {
    private int RequestID;
    private int UserID;
    private LocalDateTime FromDate;
    private LocalDateTime ToDate;
    private String Reason;
    private int StatusID;
    private int ApprovedBy;
    private LocalDateTime CreateAt;
    private LocalDateTime UpdateAt;

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

    public LocalDateTime getFromDate() {
        return FromDate;
    }

    public void setFromDate(LocalDateTime FromDate) {
        this.FromDate = FromDate;
    }

    public LocalDateTime getToDate() {
        return ToDate;
    }

    public void setToDate(LocalDateTime ToDate) {
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

    public int getApprovedBy() {
        return ApprovedBy;
    }

    public void setApprovedBy(int ApprovedBy) {
        this.ApprovedBy = ApprovedBy;
    }

    public LocalDateTime getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(LocalDateTime CreateAt) {
        this.CreateAt = CreateAt;
    }

    public LocalDateTime getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(LocalDateTime UpdateAt) {
        this.UpdateAt = UpdateAt;
    }

 
}
