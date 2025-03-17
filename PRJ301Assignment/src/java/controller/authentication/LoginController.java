
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String Username = req.getParameter("Username");
        String Password = req.getParameter("PasswordHash");
        UserDBContext db =new UserDBContext();
        User user = db.get(Username, Password);
       
        
        if(user!=null)
        {                     
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            req.getRequestDispatcher("/view/auth/home.jsp").forward(req, resp);
        }
        else
        {
            resp.getWriter().println("login failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/auth/login.html").forward(req, resp);
    }
    
}
