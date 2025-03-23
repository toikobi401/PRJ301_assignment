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

    private static final int PAGE_SIZE = 5; // Số bản ghi trên mỗi trang

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
        // Gọi LeaveRequestDBContext để lấy danh sách đơn xin nghỉ
        LeaveRequestDBContext db = new LeaveRequestDBContext();
        ArrayList<LeaveRequest> allRequests = db.list(userId);

        // Tính toán phân trang
        int totalRecords = allRequests.size();
        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
        if (totalRecords == 0) {
                        currentPage = 1; // Nếu không có bản ghi, đặt currentPage về 1
                        totalPages = 0;  // Số trang là 0
                    } else {
                        if (currentPage < 1) {
                            currentPage = 1;
                        }
                        if (currentPage > totalPages) {
                            currentPage = totalPages;
                        }
                    }
        // Lấy danh sách bản ghi cho trang hiện tại
        int start = (currentPage - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totalRecords);
        ArrayList<LeaveRequest> leaveRequests = new ArrayList<>();
        for (int i = start; i < end; i++) {
            leaveRequests.add(allRequests.get(i));
        }

        // Đặt dữ liệu vào request attribute để JSP sử dụng
        request.setAttribute("leaveRequests", leaveRequests);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        // Chuyển tiếp đến yourleavereq.jsp
        request.getRequestDispatcher("/view/function/yourleavereq.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Nếu cần xử lý POST, có thể thêm logic ở đây
        doGet(request, response); // Mặc định gọi lại doGet
    }
}