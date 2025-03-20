package controller.authentication;

import dal.UserDBContext;
import data.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Chuyển hướng đến login.jsp khi truy cập bằng GET
        resp.sendRedirect(req.getContextPath() + "/view/auth/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Kiểm tra thông tin đăng nhập
        UserDBContext userDB = new UserDBContext();
        User user = userDB.get(username, password);

        if (user != null && user.getPasswordHash().equals(password)) {
            // Đăng nhập thành công, lưu user vào session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            // Chuyển hướng đến trang chủ
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            // Đăng nhập thất bại, gửi thông báo lỗi và forward đến login.jsp
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            req.getRequestDispatcher("/view/auth/login.jsp").forward(req, resp);
        }
    }
}