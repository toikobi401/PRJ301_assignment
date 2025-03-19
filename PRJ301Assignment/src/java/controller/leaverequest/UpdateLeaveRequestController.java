package controller.leaverequest;

import dal.LeaveRequestDBContext;
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

public class UpdateLeaveRequestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để cập nhật đơn xin nghỉ.");
            return;
        }

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu người dùng không hợp lệ.");
            return;
        }
        User user = (User) userObj;

        String requestIDStr = request.getParameter("requestID");
        System.out.println("requestID từ query string (doGet): " + requestIDStr);

        if (requestIDStr == null || requestIDStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu requestID.");
            return;
        }

        try {
            int requestID = Integer.parseInt(requestIDStr);
            LeaveRequestDBContext db = new LeaveRequestDBContext();
            LeaveRequest leaveRequest = db.get(requestID);

            if (leaveRequest == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn xin nghỉ với ID: " + requestID);
                return;
            }

            System.out.println("RequestID gửi sang JSP: " + leaveRequest.getRequestID());
            request.setAttribute("user", user);
            request.setAttribute("leaveRequest", leaveRequest);

            request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "requestID không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tải đơn xin nghỉ: " + e.getMessage());
            System.out.println("Lỗi chi tiết: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để cập nhật đơn xin nghỉ.");
            return;
        }

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu người dùng không hợp lệ.");
            return;
        }
        User user = (User) userObj;
        int userId = user.getUserID();

        // Lấy requestID từ query string (thay vì từ form)
        String requestIDStr = request.getParameter("requestID");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String reason = request.getParameter("reason");

        System.out.println("requestID từ query string (doPost): " + requestIDStr);
        System.out.println("fromDate từ form: " + fromDate);
        System.out.println("toDate từ form: " + toDate);
        System.out.println("reason từ form: " + reason);

        if (requestIDStr == null || requestIDStr.trim().isEmpty() ||
            fromDate == null || fromDate.trim().isEmpty() ||
            toDate == null || toDate.trim().isEmpty() ||
            reason == null || reason.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin cần thiết.");
            return;
        }

        try {
            int requestID = Integer.parseInt(requestIDStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startLocalDate = LocalDate.parse(fromDate, formatter);
            LocalDate endLocalDate = LocalDate.parse(toDate, formatter);
            Date startDate = Date.valueOf(startLocalDate);
            Date endDate = Date.valueOf(endLocalDate);

            if (startDate.after(endDate)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ngày bắt đầu không thể sau ngày kết thúc.");
                return;
            }

            LeaveRequestDBContext db = new LeaveRequestDBContext();
            LeaveRequest leaveRequest = db.get(requestID);
            if (leaveRequest == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn xin nghỉ với ID: " + requestID);
                return;
            }

            leaveRequest.setUserID(userId);
            leaveRequest.setFromDate(startDate);
            leaveRequest.setToDate(endDate);
            leaveRequest.setReason(reason);

            db.update(leaveRequest);
            response.sendRedirect(request.getContextPath() + "/LeaveRequest/list");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "requestID không hợp lệ: " + e.getMessage());
        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng ngày không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể cập nhật đơn xin nghỉ: " + e.getMessage());
            System.out.println("Lỗi chi tiết: " + e.getMessage());
        }
    }
}