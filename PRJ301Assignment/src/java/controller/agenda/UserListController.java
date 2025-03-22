package controller.agenda;

import controller.authentication.BaseRequiredAuthenticationController;
import dal.UserDBContext;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class UserListController extends BaseRequiredAuthenticationController {

    private static final int PAGE_SIZE = 5; // Số bản ghi trên mỗi trang

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        if (!isAdmin(user)) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        try {
            // Lấy tham số tìm kiếm
            String search = req.getParameter("search");
            if (search == null) {
                search = "";
            }

            // Lấy toàn bộ danh sách người dùng (đã lọc theo search)
            UserDBContext userDB = new UserDBContext();
            ArrayList<User> allUsers = userDB.searchByName(search);

            // Tính toán phân trang
            int totalRecords = allUsers != null ? allUsers.size() : 0;
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
            ArrayList<User> users = new ArrayList<>();
            if (allUsers != null && !allUsers.isEmpty()) {
                int start = (currentPage - 1) * PAGE_SIZE;
                int end = Math.min(start + PAGE_SIZE, totalRecords);
                for (int i = start; i < end; i++) {
                    users.add(allUsers.get(i));
                }
            }

            req.setAttribute("users", users);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("totalPages", totalPages);
            req.getRequestDispatcher("/view/manage/userlist.jsp").forward(req, resp);
        } catch (Exception e) {
            System.out.println("Lỗi trong UserListController: " + e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi lấy dữ liệu: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        doGet(req, resp); // Mặc định gọi lại doGet
    }
}