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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        if (!isAdmin(user)) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        UserDBContext userDB = new UserDBContext();
        ArrayList<User> users = userDB.list();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/view/manage/userlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        // Không cần xử lý POST cho chức năng này
    }
}