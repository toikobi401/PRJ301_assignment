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
        // Gọi eaveRequestDBContext để lấy danh sách đơn xin nghỉ
        LeaveRequestDBContext db = new LeaveRequestDBContext();
        ArrayList<LeaveRequest> leaveRequests = db.list(userId);
        System.out.println("Danh sách đơn xin nghỉ phép của UserID: " + userId);

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