
package controller;

import dal.DBContext;
import dal.RoleDBContext;
import dal.UserDBContext;
import dal.DepartmentDBContext;
import dal.UserRoleDBContext;

import data.Role;
import data.User;
import data.Department;
import data.UserRole;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class Test extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Test</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Test at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

            DBContext<User> userDB = new UserDBContext();
        DBContext<Role> roleDB = new RoleDBContext();
        DBContext<Department> depDB = new DepartmentDBContext();
        DBContext<UserRole> userroleDB = new UserRoleDBContext();

        

        List<User> users = userDB.list();
        List<Role> roles = roleDB.list();
        List<Department> deps = depDB.list();
        List<UserRole> userroles = userroleDB.list();




        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>User List</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>User List</h1>");
            
            out.println("<ul>");
            for (User user : users) {
                out.println("<li>" + user.getUserID() + " - " + user.getUsername() + "</li>");
            }
            out.println("</ul>");
            
            out.println();
            
            out.println("<ul>");
            for (Role role : roles) {
                out.println("<li>" + role.getRoleID() + " - " + role.getRoleName()+ "</li>");
            }
            out.println("</ul>");
            
            out.println();
            out.println("<ul>");
            for (Department dep : deps) {
                out.println("<li>" + dep.getDepartmentID()+ " - " + dep.getDepartmentName()+ "</li>");
            }
            out.println("</ul>");
            
            out.println();
            out.println("<ul>");
            for (UserRole userrole : userroles) {
                out.println("<li>" + userrole.getUserRoleID()+ " - " + userrole.getUserID()+ " - " + userrole.getRoleID() + "</li>");
            }

            
            out.println("</body>");
            out.println("</html>");
        }
    }


   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
