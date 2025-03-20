//package controller.leaverequest;
//
//import controller.authentication.BaseRequiredAuthenticationController;
//import dal.LeaveRequestDBContext;
//import data.LeaveRequestWithUser;
//import data.User;
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//
//public class ManageRequestServletController extends BaseRequiredAuthenticationController {
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
//        // Để trống vì hiện tại chỉ cần hiển thị danh sách
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
//        // Lấy UserID của người dùng hiện tại (manager)
//        int managerId = user.getUserID();
//
//        // Lấy danh sách đơn xin nghỉ phép thuộc quyền quản lý của manager
//        LeaveRequestDBContext db = new LeaveRequestDBContext();
//        ArrayList<LeaveRequestWithUser> leaveRequests = db.listForManager(managerId);
//
//        // Truyền danh sách đơn vào request để hiển thị trong JSP
//        req.setAttribute("leaveRequests", leaveRequests);
//        req.getRequestDispatcher("/view/function/manageleavereq.jsp").forward(req, resp);
//    }
//}