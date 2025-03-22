package controller.agenda;

import controller.authentication.BaseRequiredAuthenticationController;
import dal.UserWorkingDetailDBContext;
import data.LeaveRequest;
import data.User;
import data.UserWorkingDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class UserWorkingDetailController extends BaseRequiredAuthenticationController {

    private static final int PAGE_SIZE = 5; // Số bản ghi trên mỗi trang

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

            // Lấy toàn bộ danh sách đơn xin nghỉ phép
            UserWorkingDetailDBContext userWorkingDetailDB = new UserWorkingDetailDBContext();
            UserWorkingDetail userWorkingDetail = userWorkingDetailDB.getUserWorkingDetail(userId);
            if (userWorkingDetail == null || userWorkingDetail.getUser() == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user với userId: " + userId);
                return;
            }

            ArrayList<LeaveRequest> allRequests = userWorkingDetail.getLeaveRequests();
            System.out.println("Tên user: " + userWorkingDetail.getUser().getFullName()); // Debug: Kiểm tra thông tin user
            System.out.println("Số lượng đơn xin nghỉ: " + (allRequests != null ? allRequests.size() : 0)); // Debug: Kiểm tra số lượng đơn

            // Tính toán phân trang
            int totalRecords = allRequests != null ? allRequests.size() : 0;
            int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
            int currentPage = 1;
            try {
                currentPage = Integer.parseInt(req.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
            if (currentPage < 1) currentPage = 1;
            if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;

            // Lấy danh sách bản ghi cho trang hiện tại
            ArrayList<LeaveRequest> leaveRequests = new ArrayList<>();
            if (allRequests != null && !allRequests.isEmpty()) {
                int start = (currentPage - 1) * PAGE_SIZE;
                int end = Math.min(start + PAGE_SIZE, totalRecords);
                for (int i = start; i < end; i++) {
                    leaveRequests.add(allRequests.get(i));
                }
            }

            // Cập nhật danh sách đơn xin nghỉ phép trong userWorkingDetail
            userWorkingDetail.setLeaveRequests(leaveRequests);

            req.setAttribute("userWorkingDetail", userWorkingDetail);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("totalPages", totalPages);
            req.getRequestDispatcher("/view/manage/userworkingdetail.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "userId hoặc page không hợp lệ.");
        } catch (Exception e) {
            System.out.println("Lỗi trong UserWorkingDetailController: " + e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy dữ liệu: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        doGet(req, resp); // Mặc định gọi lại doGet
    }
}