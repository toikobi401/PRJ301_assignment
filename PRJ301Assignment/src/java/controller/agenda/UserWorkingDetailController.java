package controller.agenda;

import controller.authentication.BaseRequiredAuthenticationController;
import dal.UserWorkingDetailDBContext;
import data.User;
import data.UserWorkingDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserWorkingDetailController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        if (!isAdmin(user)) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        String userIdStr = req.getParameter("userId");
        if (userIdStr == null || userIdStr.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu userId.");
            return;
        }
        try {
            int userId = Integer.parseInt(userIdStr);
            System.out.println("UserID: " + userId); // Debug: Kiểm tra userId

            UserWorkingDetailDBContext userWorkingDetailDB = new UserWorkingDetailDBContext();
            UserWorkingDetail userWorkingDetail = userWorkingDetailDB.getUserWorkingDetail(userId);
            if (userWorkingDetail == null || userWorkingDetail.getUser() == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user với userId: " + userId);
                return;
            }

            System.out.println("Tên user: " + userWorkingDetail.getUser().getFullName()); // Debug: Kiểm tra thông tin user
            System.out.println("Số lượng đơn xin nghỉ: " + (userWorkingDetail.getLeaveRequests() != null ? userWorkingDetail.getLeaveRequests().size() : 0)); // Debug: Kiểm tra số lượng đơn

            req.setAttribute("userWorkingDetail", userWorkingDetail);
            req.getRequestDispatcher("/view/manage/userworkingdetail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "userId không hợp lệ.");
        } catch (Exception e) {
            System.out.println("Lỗi trong UserWorkingDetailController: " + e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy dữ liệu: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        // Không cần xử lý POST cho chức năng này
    }
}