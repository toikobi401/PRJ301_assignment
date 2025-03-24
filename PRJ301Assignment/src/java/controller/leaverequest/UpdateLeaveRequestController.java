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
            request.setAttribute("errorMessage", "Bạn cần đăng nhập để cập nhật đơn xin nghỉ.");
            request.getRequestDispatcher("/view/auth/login.jsp").forward(request, response);
            return;
        }

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) {
            request.setAttribute("errorMessage", "Dữ liệu người dùng không hợp lệ.");
            request.getRequestDispatcher("/view/auth/login.jsp").forward(request, response);
            return;
        }
        User user = (User) userObj;

        String requestIDStr = request.getParameter("requestID");
        System.out.println("requestID từ query string (doGet): " + requestIDStr);

        if (requestIDStr == null || requestIDStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Thiếu requestID.");
            request.getRequestDispatcher("/view/function/listleaverequest.jsp").forward(request, response);
            return;
        }

        try {
            int requestID = Integer.parseInt(requestIDStr);
            LeaveRequestDBContext db = new LeaveRequestDBContext();
            LeaveRequest leaveRequest = db.newget(requestID);

            if (leaveRequest == null) {
                request.setAttribute("errorMessage", "Không tìm thấy đơn xin nghỉ với ID: " + requestID);
                request.getRequestDispatcher("/view/function/listleaverequest.jsp").forward(request, response);
                return;
            }

            System.out.println("RequestID gửi sang JSP: " + leaveRequest.getRequestID());
            request.setAttribute("user", user);
            request.setAttribute("leaveRequest", leaveRequest);

            request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "requestID không hợp lệ: " + e.getMessage());
            request.getRequestDispatcher("/view/function/listleaverequest.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi khi tải đơn xin nghỉ: " + e.getMessage());
            request.getRequestDispatcher("/view/function/listleaverequest.jsp").forward(request, response);
            System.out.println("Lỗi chi tiết: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            request.setAttribute("errorMessage", "Bạn cần đăng nhập để cập nhật đơn xin nghỉ.");
            request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
            return;
        }

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) {
            request.setAttribute("errorMessage", "Dữ liệu người dùng không hợp lệ.");
            request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
            return;
        }
        User user = (User) userObj;
        int userId = user.getUserID();

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
            request.setAttribute("errorMessage", "Thiếu thông tin cần thiết.");
            request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
            return;
        }

        try {
            int requestID = Integer.parseInt(requestIDStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startLocalDate = LocalDate.parse(fromDate, formatter);
            LocalDate endLocalDate = LocalDate.parse(toDate, formatter);
            java.sql.Date startDate = java.sql.Date.valueOf(startLocalDate);
            java.sql.Date endDate = java.sql.Date.valueOf(endLocalDate);

            // Kiểm tra ngày bắt đầu so với ngày hiện tại
            LocalDate today = LocalDate.now();
            if (startLocalDate.isBefore(today)) {
                request.setAttribute("errorMessage", "Ngày bắt đầu không được nằm trong quá khứ. Vui lòng chọn ngày từ hôm nay trở đi.");
                request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
                return;
            }

            if (startDate.after(endDate)) {
                request.setAttribute("errorMessage", "Ngày bắt đầu không thể sau ngày kết thúc.");
                request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
                return;
            }

            LeaveRequestDBContext db = new LeaveRequestDBContext();
            LeaveRequest leaveRequest = db.newget(requestID);
            if (leaveRequest == null) {
                request.setAttribute("errorMessage", "Không tìm thấy đơn xin nghỉ với ID: " + requestID);
                request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
                return;
            }

            if (db.hasOverlappingLeaveRequest(userId, startDate, endDate, requestID)) {
                request.setAttribute("errorMessage", "Khoảng thời gian này trùng với một đơn xin nghỉ khác của bạn.");
                request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
                return;
            }

            leaveRequest.setFromDate(startDate);
            leaveRequest.setToDate(endDate);
            leaveRequest.setReason(reason);

            db.update(leaveRequest);
            response.sendRedirect(request.getContextPath() + "/LeaveRequest/list");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "requestID không hợp lệ: " + e.getMessage());
            request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
        } catch (DateTimeParseException e) {
            request.setAttribute("errorMessage", "Định dạng ngày không hợp lệ: " + e.getMessage());
            request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Không thể cập nhật đơn xin nghỉ: " + e.getMessage());
            request.getRequestDispatcher("/view/function/updateleavereq.jsp").forward(request, response);
            System.out.println("Lỗi chi tiết: " + e.getMessage());
        }
    }
}