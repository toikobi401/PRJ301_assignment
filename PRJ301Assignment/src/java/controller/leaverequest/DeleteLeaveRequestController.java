package controller.leaverequest;

import dal.LeaveRequestDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteLeaveRequestController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy requestID từ form
            String requestIDStr = request.getParameter("requestID");
            if (requestIDStr == null || requestIDStr.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu requestID.");
                return;
            }

            int requestID = Integer.parseInt(requestIDStr);

            // Gọi LeaveRequestDBContext để xóa
            LeaveRequestDBContext db = new LeaveRequestDBContext();
            db.delete(requestID); // Sử dụng phương thức delete(int requestID) mới

            // Chuyển hướng đến danh sách sau khi xóa thành công
            response.sendRedirect(request.getContextPath() + "/LeaveRequest/list");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "requestID không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể xóa đơn xin nghỉ: " + e.getMessage());
            System.out.println("Lỗi chi tiết: " + e.getMessage());
        }
    }
}