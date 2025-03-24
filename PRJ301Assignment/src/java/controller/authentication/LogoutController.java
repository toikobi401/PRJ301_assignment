package controller.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy session hiện tại (nếu có)
        HttpSession session = req.getSession(false);
        if (session != null) {
            // Hủy session
            session.invalidate();
        }
        // Chuyển hướng về trang đăng nhập (login.jsp)
        resp.sendRedirect(req.getContextPath() + "/view/auth/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp); // Gọi lại doGet
    }
}