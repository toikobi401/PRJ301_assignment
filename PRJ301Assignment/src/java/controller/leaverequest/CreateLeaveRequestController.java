package controller.leaverequest;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import data.LeaveRequest;
import data.User;
import dal.LeaveRequestDBContext;

@WebServlet("/LeaveRequest/create") // Khớp với web.xml
public class CreateLeaveRequestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy session
        HttpSession session = request.getSession(false); // Không tạo session mới nếu không tồn tại

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để tạo đơn xin nghỉ.");
            return;
        }

        // Chuyển hướng đến leaverequest.jsp để hiển thị form
        request.getRequestDispatcher("/view/function/leaverequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy session
        HttpSession session = request.getSession(false); // Không tạo session mới nếu không tồn tại

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để tạo đơn xin nghỉ.");
            return;
        }

        // Lấy đối tượng user từ session và lấy userId
        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof User)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu người dùng không hợp lệ.");
            return;
        }
        User user = (User) userObj; // Ép kiểu về User
        int userId = user.getUserID(); // Lấy userId từ đối tượng user

        // Lấy dữ liệu từ form
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String reason = request.getParameter("reason");

        // Debug: In giá trị tham số để kiểm tra
        System.out.println("startDate: " + startDateStr);
        System.out.println("endDate: " + endDateStr);
        System.out.println("reason: " + reason);

        // Kiểm tra các giá trị null hoặc rỗng
        if (startDateStr == null || startDateStr.trim().isEmpty() ||
            endDateStr == null || endDateStr.trim().isEmpty() ||
            reason == null || reason.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin cần thiết.");
            return;
        }

        try {
            // Chuyển đổi chuỗi ngày thành java.sql.Date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startLocalDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endLocalDate = LocalDate.parse(endDateStr, formatter);
            Date startDate = Date.valueOf(startLocalDate);
            Date endDate = Date.valueOf(endLocalDate);

            // Kiểm tra logic ngày
            if (startDate.after(endDate)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ngày bắt đầu không thể sau ngày kết thúc.");
                return;
            }

            // Tạo đối tượng LeaveRequest
            LeaveRequest leaveRequest = new LeaveRequest();
            leaveRequest.setUserID(userId); // Sử dụng userId từ session
            leaveRequest.setFromDate(startDate);
            leaveRequest.setToDate(endDate);
            leaveRequest.setReason(reason);
            leaveRequest.setStatusID(1); // Giả định: 1 = "Pending". Thay đổi nếu cần.

            // Lưu vào cơ sở dữ liệu
            LeaveRequestDBContext leaveRequestDB = new LeaveRequestDBContext();
            leaveRequestDB.insert(leaveRequest);

            // Chuyển hướng đến trang danh sách đơn
            response.sendRedirect("view/function/yourleavereq.jsp");

        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Định dạng ngày không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể tạo đơn xin nghỉ: " + e.getMessage());
        }
    }
}