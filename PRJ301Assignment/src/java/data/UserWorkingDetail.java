package data;

import java.util.ArrayList;

public class UserWorkingDetail {
    private User user;
    private ArrayList<LeaveRequest> leaveRequests;

    public UserWorkingDetail() {
        this.leaveRequests = new ArrayList<>();
    }

    public UserWorkingDetail(User user, ArrayList<LeaveRequest> leaveRequests) {
        this.user = user;
        this.leaveRequests = leaveRequests != null ? leaveRequests : new ArrayList<>();
    }

    // Getters và setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<LeaveRequest> getLeaveRequests() {
        return leaveRequests;
    }

    public void setLeaveRequests(ArrayList<LeaveRequest> leaveRequests) {
        this.leaveRequests = leaveRequests != null ? leaveRequests : new ArrayList<>();
    }
}