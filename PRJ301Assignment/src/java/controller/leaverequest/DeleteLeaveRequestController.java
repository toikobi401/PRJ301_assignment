package controller.leaverequest;

import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteLeaveRequestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để xóa đơn xin nghỉ.");
            return;
        }

        // Lấy requestID từ query string
        String requestIDStr = request.getParameter("requestID");
        System.out.println("requestID từ query string: " + requestIDStr); // Ghi log dữ liệu từ query string

        // Kiểm tra dữ liệu từ query string
        if (requestIDStr == null || requestIDStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu requestID.");
            return;
        }

        try {
            int requestID = Integer.parseInt(requestIDStr);
            System.out.println("requestID đã parse: " + requestID); // Ghi log giá trị đã parse

            // Gọi LeaveRequestDBContext để xóa
            LeaveRequestDBContext db = new LeaveRequestDBContext();
            db.delete(requestID);

            // Chuyển hướng đến danh sách sau khi xóa thành công
            response.sendRedirect(request.getContextPath() + "/LeaveRequest/list");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "requestID không hợp lệ: " + e.getMessage());
            System.out.println("Lỗi chi tiết: " + e.getMessage()); // Ghi log lỗi
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể xóa đơn xin nghỉ: " + e.getMessage());
            System.out.println("Lỗi chi tiết: " + e.getMessage()); // Ghi log lỗi
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để xóa đơn xin nghỉ.");
            return;
        }

        // Trả về lỗi nếu gọi POST
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Phương thức POST không được hỗ trợ cho xóa.");
    }
}