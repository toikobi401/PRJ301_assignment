//package controller.leaverequest;
//
//import controller.authentication.BaseRequiredAuthenticationController;
//import dal.DBContext;
//import data.LeaveRequest;
//import data.User;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/ManageRequestServlet")
//public class ManageRequestServlet extends BaseRequiredAuthenticationController {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
//        // Kiểm tra RoleID >= 2
//        if (!hasManagerRole(user)) {
//            resp.getWriter().println("Access denied! Only managers (RoleID >= 2) can access this feature.");
//            return;
//        }
//
//        String action = req.getParameter("action");
//        if (action != null) {
//            int requestId = Integer.parseInt(req.getParameter("requestId"));
//            int statusId = action.equals("approve") ? 1 : 2;
//            updateStatus(requestId, statusId);
//        }
//
//        ArrayList<LeaveRequest> allRequests = new ArrayList<>();
//        DBContext db = new DBContext() {
//            @Override
//            public ArrayList list() {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//
//            @Override
//            public Object get(int id) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//
//            @Override
//            public void insert(Object model) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//
//            @Override
//            public void update(Object model) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//
//            @Override
//            public void delete(Object model) {
//                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//            }
//        };
//        Connection conn = null;
//        try {
//            conn = db.getConnection();
//            String sql = "SELECT lr.*, u.Username FROM LeaveRequest lr JOIN dbo.User u ON lr.UserID = u.UserID";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                LeaveRequest lr = new LeaveRequest();
//                lr.setRequestID(rs.getInt("RequestID"));
//                lr.setUserID(rs.getInt("UserID"));
//                lr.setUsername(rs.getString("Username"));
//                lr.setRequestDate(rs.getString("RequestDate"));
//                lr.setReason(rs.getString("Reason"));
//                lr.setStatusID(rs.getInt("StatusID"));
//                allRequests.add(lr);
//            }
//            req.setAttribute("allRequests", allRequests);
//            req.getRequestDispatcher("function/managerequest.jsp").forward(req, resp);
//        } catch (Exception e) {
//            e.printStackTrace();
//            req.setAttribute("error", "Error loading requests.");
//            req.getRequestDispatcher("function/managerequest.jsp").forward(req, resp);
//        } finally {
//            db.closeConnection(conn);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
//        // Không cần doPost cho managerequest vì chỉ dùng GET để duyệt/từ chối
//        doGet(req, resp, user);
//    }
//
//    private void updateStatus(int requestId, int statusId) {
//        DBContext db = new DBContext();
//        Connection conn = null;
//        try {
//            conn = db.getConnection();
//            String sql = "UPDATE LeaveRequest SET StatusID = ? WHERE RequestID = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, statusId);
//            ps.setInt(2, requestId);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            db.closeConnection(conn);
//        }
//    }
//
//    // Hàm kiểm tra RoleID >= 2
//    private boolean hasManagerRole(User user) {
//        // Giả định User có thuộc tính RoleID hoặc danh sách Role
//        for (data.Role role : user.getRoles()) {
//            if (role.getRoleID() >= 2) { // Kiểm tra RoleID
//                return true;
//            }
//        }
//        return false;
//    }
//}