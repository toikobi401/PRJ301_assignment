package controller.leaverequest;

import data.LeaveRequest;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import dal.LeaveRequestDBContext;

public class CreateLeaveRequestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để tạo đơn xin nghỉ.");
            return;
        }

        // Lấy thông tin người dùng từ session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu người dùng không hợp lệ.");
            return;
        }


        // Truyền thông tin user vào request để sử dụng trong leaverequest.jsp
        request.setAttribute("user", user);

        // Chuyển tiếp đến leaverequest.jsp
        request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để tạo đơn xin nghỉ.");
            return;
        }

        // Nguồn 1: Lấy thông tin người dùng từ session
        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu người dùng không hợp lệ.");
            return;
        }
        User user = (User) userObj;
        int userId = user.getUserID();
        System.out.println("UserID từ session trong doPost: " + userId); // Ghi log để kiểm tra

        // Nguồn 2: Lấy dữ liệu từ form (leaverequest.jsp)
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String reason = request.getParameter("reason");

        // Ghi log dữ liệu từ form
        System.out.println("startDate từ form: " + startDateStr);
        System.out.println("endDate từ form: " + endDateStr);
        System.out.println("reason từ form: " + reason);

        // Kiểm tra dữ liệu từ form
        if (startDateStr == null || startDateStr.trim().isEmpty() ||
            endDateStr == null || endDateStr.trim().isEmpty() ||
            reason == null || reason.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin cần thiết.");
            return;
        }

        try {
            // Xử lý dữ liệu ngày
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startLocalDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endLocalDate = LocalDate.parse(endDateStr, formatter);
            Date startDate = Date.valueOf(startLocalDate);
            Date endDate = Date.valueOf(endLocalDate);

            // Kiểm tra ngày hợp lệ
            if (startDate.after(endDate)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ngày bắt đầu không thể sau ngày kết thúc.");
                return;
            }

            // Kết hợp dữ liệu từ session và form để tạo LeaveRequest
            LeaveRequest leaveRequest = new LeaveRequest();
            leaveRequest.setUserID(userId); // Từ session
            leaveRequest.setFromDate(startDate); // Từ form
            leaveRequest.setToDate(endDate); // Từ form
            leaveRequest.setReason(reason); // Từ form
            leaveRequest.setStatusID(3); // Mặc định là 3 như yêu cầu

            // Lưu vào cơ sở dữ liệu
            LeaveRequestDBContext leaveRequestDB = new LeaveRequestDBContext();
            leaveRequestDB.insert(leaveRequest);

            // Chuyển hướng sau khi thành công
            response.sendRedirect("/view/function/yourleavereq.jsp");

        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng ngày không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể tạo đơn xin nghỉ: " + e.getMessage());
            System.out.println("Lỗi chi tiết: " + e.getMessage()); // Ghi log lỗi
        }
    }
}