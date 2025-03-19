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
import java.util.ArrayList;


public class YourLeaveRequestController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để xem danh sách đơn xin nghỉ.");
            return;
        }

        // Lấy thông tin người dùng từ session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Dữ liệu người dùng không hợp lệ.");
            return;
        }

        // Lấy UserID từ đối tượng User
        int userId = user.getUserID();
        System.out.println(userId);

        // Gọi eaveRequestDBContext để lấy danh sách đơn xin nghỉ
        LeaveRequestDBContext db = new LeaveRequestDBContext();
        ArrayList<LeaveRequest> leaveRequests = db.list(userId);
        System.out.println("Danh sách đơn xin nghỉ phép của UserID: " + userId);
        if (leaveRequests.isEmpty()) {
            System.out.println("Không có đơn xin nghỉ phép nào.");
        } else {
            for (LeaveRequest lr : leaveRequests) {
                System.out.println("-------------------------");
                System.out.println("UserID: " + lr.getUserID());
                System.out.println("FromDate: " + lr.getFromDate());
                System.out.println("ToDate: " + lr.getToDate());
                System.out.println("Reason: " + lr.getReason());
                System.out.println("StatusID: " + lr.getStatusID());
                System.out.println("StatusName: " + (lr.getLeaveStatus() != null ? lr.getLeaveStatus().getStatusName() : "N/A"));
                System.out.println("Description: " + (lr.getLeaveStatus() != null ? lr.getLeaveStatus().getDescription() : "N/A"));
                System.out.println("ApprovedBy: " + (lr.getApprovedBy() != null ? lr.getApprovedBy() : "Chưa phê duyệt"));
                System.out.println("CreatedAt: " + lr.getCreateAt());
                System.out.println("UpdatedAt: " + lr.getUpdateAt());
                System.out.println("-------------------------");
            }
        }

        // Đặt danh sách vào request attribute để JSP sử dụng
        request.setAttribute("leaveRequests", leaveRequests);
        
        // Chuyển tiếp đến yourleavereq.jsp
        request.getRequestDispatcher("/view/function/yourleavereq.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Nếu cần xử lý POST, có thể thêm logic ở đây
        doGet(request, response); // Mặc định gọi lại doGet
    }
}   