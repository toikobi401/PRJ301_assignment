package controller.leaverequest;

import dal.LeaveRequestDBContext;
import data.LeaveRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.*;


public class CreateLeaveRequestController extends LeaveRequestDBContext {
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int UserID = Integer.parseInt((String) session.getAttribute("UserID"));

        String reason = request.getParameter("reason");
        String fromDateStr = request.getParameter("fromDate"); // Chuỗi dạng "yyyy-MM-dd"
        String toDateStr = request.getParameter("toDate");     // Chuỗi dạng "yyyy-MM-dd"

        // Chuyển đổi chuỗi thành java.sql.Date
        Date fromDate = Date.valueOf(fromDateStr);
        Date toDate = Date.valueOf(toDateStr);

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setUserID(UserID);
        leaveRequest.setFromDate(fromDate);
        leaveRequest.setToDate(toDate);
        leaveRequest.setReason(reason);
        leaveRequest.setStatusID(3); // Ví dụ: trạng thái mặc định

        LeaveRequestDBContext dbContext = new LeaveRequestDBContext();
        try {
            dbContext.insert(leaveRequest);
            response.sendRedirect("/view/function/success.jsp");
        } catch (IOException e) {
            response.sendRedirect("/view/function/error.jsp");
        }
    }
 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/function/leaverequest.jsp").forward(req, resp);
    }
}