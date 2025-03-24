package controller.leaverequest;

import controller.authentication.BaseRequiredAuthenticationController;
import dal.LeaveRequestDBContext;
import dal.UserDBContext;
import data.LeaveRequest;
import data.Role;
import data.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

public class ManageRequestServletController extends BaseRequiredAuthenticationController {

    private static final int PAGE_SIZE = 5; // Số bản ghi trên mỗi trang

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        // Xử lý POST nếu cần (ví dụ: duyệt hoặc từ chối đơn)
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        LeaveRequestDBContext leaveDB = new LeaveRequestDBContext();
        UserDBContext userDB = new UserDBContext();
        ArrayList<Object[]> leaveRequestsWithUser = new ArrayList<>();

        // Lấy RoleID của user hiện tại
        boolean isAdmin = false; // RoleID = 1 (Admin)
        boolean isManager = false; // RoleID = 2 (Manager)
        for (Role role : user.getRoles()) {
            if (role.getRoleID() == 1) {
                isAdmin = true;
                break;
            } else if (role.getRoleID() == 2) {
                isManager = true;
            }
        }

        // Lấy tất cả đơn có StatusID = 3 (chờ duyệt)
        ArrayList<LeaveRequest> allPendingRequests = new ArrayList<>();
        if (isAdmin) {
            ArrayList<LeaveRequest> allRequests = leaveDB.list();
            for (LeaveRequest lr : allRequests) {
                if (lr.getStatusID() == 3) {
                    allPendingRequests.add(lr);
                }
            }
        } else if (isManager) {
            ArrayList<User> usersInDept = userDB.list();
            for (User u : usersInDept) {
                if (u.getDepartmentID() == user.getDepartmentID() && u.getUserID() != user.getUserID()) {
                    ArrayList<LeaveRequest> userRequests = leaveDB.list(u.getUserID());
                    for (LeaveRequest lr : userRequests) {
                        if (lr.getStatusID() == 3) {
                            allPendingRequests.add(lr);
                        }
                    }
                }
            }
        }

        // Tính toán phân trang
        int totalRecords = allPendingRequests.size();
        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages && totalPages > 0) currentPage = totalPages; // Chỉ điều chỉnh nếu totalPages > 0

        // Kiểm tra nếu không có đơn chờ duyệt
        if (totalRecords == 0) {
            req.setAttribute("leaveRequests", leaveRequestsWithUser); // Danh sách rỗng
            req.setAttribute("currentPage", 1);
            req.setAttribute("totalPages", 0);
            req.getRequestDispatcher("/view/manage/managerequest.jsp").forward(req, resp);
            return;
        }

        // Lấy ánh xạ UserID với FullName
        Map<Integer, String> userFullNameMap = leaveDB.getUserFullNameMap();

        // Lấy danh sách bản ghi cho trang hiện tại
        int start = (currentPage - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totalRecords);
        for (int i = start; i < end; i++) {
            LeaveRequest lr = allPendingRequests.get(i);
            String fullName = userFullNameMap.get(lr.getUserID());
            leaveRequestsWithUser.add(new Object[]{lr, fullName != null ? fullName : "Unknown"});
        }

        // Đưa dữ liệu vào request
        req.setAttribute("leaveRequests", leaveRequestsWithUser);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/view/manage/managerequest.jsp").forward(req, resp);
    }
}