package controller.leaverequest;

import controller.authentication.BaseRequiredAuthenticationController;
import dal.LeaveRequestDBContext;
import data.LeaveRequest;
import data.Role;
import data.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

public class ApproveLeaveRequestController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        // Kiểm tra phân quyền
        boolean isAdmin = false;
        boolean isManager = false;
        for (Role role : user.getRoles()) {
            if (role.getRoleID() == 1) { // Admin
                isAdmin = true;
                break;
            } else if (role.getRoleID() == 2) { // Manager
                isManager = true;
            }
        }

        if (!isAdmin && !isManager) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền phê duyệt đơn xin nghỉ phép.");
            return;
        }

        // Lấy requestID từ tham số
        int requestID;
        try {
            requestID = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID đơn xin nghỉ không hợp lệ.");
            return;
        }

        // Lấy đơn xin nghỉ từ cơ sở dữ liệu
        LeaveRequestDBContext leaveDB = new LeaveRequestDBContext();
        LeaveRequest leaveRequest = leaveDB.newget(requestID);
        if (leaveRequest == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn xin nghỉ phép với ID: " + requestID);
            return;
        }

        // Kiểm tra trạng thái hiện tại của đơn
        if (leaveRequest.getStatusID() != 3) { // Chỉ cho phép phê duyệt đơn đang "Chờ duyệt"
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Đơn này đã được xử lý trước đó.");
            return;
        }

        // Cập nhật trạng thái đơn thành "Đã phê duyệt" (StatusID = 1)
        leaveRequest.setStatusID(1);
        leaveRequest.setApprovedBy(user.getUserID()); // Ghi lại người phê duyệt
        leaveRequest.setUpdateAt(new Timestamp(System.currentTimeMillis())); // Cập nhật thời gian

        // Lưu thay đổi vào cơ sở dữ liệu
        leaveDB.update(leaveRequest);

        // Chuyển hướng về trang quản lý đơn
        resp.sendRedirect(req.getContextPath() + "/LeaveRequest/manage");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        doGet(req, resp, user); // Gọi lại doGet
    }
}