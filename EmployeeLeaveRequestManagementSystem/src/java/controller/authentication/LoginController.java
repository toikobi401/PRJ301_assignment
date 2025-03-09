
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
        String username = req.getParameter("Username");
        String password = req.getParameter("PasswordHash");
        UserDBContext db =new UserDBContext();
        User user = db.get(username, password);
       
        
        if(user!=null)
        {
            UserDBContext u = new UserDBContext();
            
         
            HttpSession session = req.getSession();
            session.setAttribute("user", u);
            resp.sendRedirect("view/auth/welcome.jsp");
        }
        else
        {
            resp.getWriter().println("login failed!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("view/auth/login.html").forward(req, resp);
    }
    
}
