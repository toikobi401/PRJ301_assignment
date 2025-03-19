//package controller.leaverequest;
//
//import dal.DBContext;
//import data.LeaveRequest;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//
//@WebServlet("/EditLeaveRequestServlet")
//public class EditLeaveRequestServlet extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int requestId = Integer.parseInt(request.getParameter("requestId"));
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
//            String sql = "SELECT * FROM LeaveRequest WHERE RequestID = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, requestId);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                LeaveRequest lr = new LeaveRequest();
//                lr.setRequestID(rs.getInt("RequestID"));
//                lr.setUserID(rs.getInt("UserID")); // Lấy UserID
//                lr.setRequestDate(rs.getString("RequestDate"));
//                lr.setReason(rs.getString("Reason"));
//                lr.setStatusID(rs.getInt("StatusID"));
//                request.setAttribute("request", lr);
//            }
//            request.getRequestDispatcher("function/editLeaveRequest.jsp").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("error", "Error loading request.");
//            request.getRequestDispatcher("function/editLeaveRequest.jsp").forward(request, response);
//        } finally {
//            db.closeConnection(conn);
//        }
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int requestId = Integer.parseInt(request.getParameter("requestId"));
//        // int userId = Integer.parseInt(request.getParameter("userId")); // Không cần sửa UserID
//        String requestDate = request.getParameter("requestDate");
//        String reason = request.getParameter("reason");
//        int statusId = Integer.parseInt(request.getParameter("statusId"));
//
//        DBContext db = new DBContext() {    };
//        Connection conn = null;
//        try {
//            conn = db.getConnection();
//            String sql = "UPDATE LeaveRequest SET RequestDate = ?, Reason = ?, StatusID = ? WHERE RequestID = ?";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, requestDate);
//            ps.setString(2, reason);
//            ps.setInt(3, statusId);
//            ps.setInt(4, requestId);
//            ps.executeUpdate();
//            response.sendRedirect("YourLeaveRequestServlet");
//        } catch (Exception e) {
//            e.printStackTrace();
//            request.setAttribute("error", "Error updating request.");
//            request.getRequestDispatcher("function/editLeaveRequest.jsp").forward(request, response);
//        } finally {
//            db.closeConnection(conn);
//        }
//    }
//}