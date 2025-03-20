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
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            request.setAttribute("errorMessage", "Bạn cần đăng nhập để tạo đơn xin nghỉ.");
            request.getRequestDispatcher("/view/auth/login.jsp").forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("errorMessage", "Dữ liệu người dùng không hợp lệ.");
            request.getRequestDispatcher("/view/auth/login.jsp").forward(request, response);
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            request.setAttribute("errorMessage", "Bạn cần đăng nhập để tạo đơn xin nghỉ.");
            request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
            return;
        }

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) {
            request.setAttribute("errorMessage", "Dữ liệu người dùng không hợp lệ.");
            request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
            return;
        }
        User user = (User) userObj;
        int userId = user.getUserID();

        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String reason = request.getParameter("reason");

        System.out.println("startDate từ form: " + fromDate);
        System.out.println("endDate từ form: " + toDate);
        System.out.println("reason từ form: " + reason);

        if (fromDate == null || fromDate.trim().isEmpty() ||
            toDate == null || toDate.trim().isEmpty() ||
            reason == null || reason.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Thiếu thông tin cần thiết.");
            request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startLocalDate = LocalDate.parse(fromDate, formatter);
            LocalDate endLocalDate = LocalDate.parse(toDate, formatter);
            java.sql.Date startDate = java.sql.Date.valueOf(startLocalDate);
            java.sql.Date endDate = java.sql.Date.valueOf(endLocalDate);

            if (startDate.after(endDate)) {
                request.setAttribute("errorMessage", "Ngày bắt đầu không thể sau ngày kết thúc.");
                request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
                return;
            }

            LeaveRequestDBContext leaveRequestDB = new LeaveRequestDBContext();
            if (leaveRequestDB.hasOverlappingLeaveRequest(userId, startDate, endDate, null)) {
                request.setAttribute("errorMessage", "Khoảng thời gian này trùng với một đơn xin nghỉ khác của bạn.");
                request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
                return;
            }

            LeaveRequest leaveRequest = new LeaveRequest();
            leaveRequest.setUserID(userId);
            leaveRequest.setFromDate(startDate);
            leaveRequest.setToDate(endDate);
            leaveRequest.setReason(reason);
            leaveRequest.setStatusID(3); // Chờ duyệt
            leaveRequest.setApprovedBy(null);

            leaveRequestDB.insert(leaveRequest);
            response.sendRedirect(request.getContextPath() + "/view/function/success.jsp");

        } catch (DateTimeParseException e) {
            request.setAttribute("errorMessage", "Định dạng ngày không hợp lệ: " + e.getMessage());
            request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Không thể tạo đơn xin nghỉ: " + e.getMessage());
            request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
            System.out.println("Lỗi chi tiết: " + e.getMessage());
        }
    }
}