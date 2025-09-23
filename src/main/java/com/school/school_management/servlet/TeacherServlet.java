/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.school.school_management.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/teacher")
public class TeacherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }
        String role = (String) session.getAttribute("role");
        if (!"TEACHER".equals(role) && !"ADMIN".equals(role)) {
            resp.getWriter().println("<html><body><h3>Không có quyền truy cập trang giáo viên.</h3><p><a href='" + req.getContextPath() + "/login.html'>Quay lại</a></p></body></html>");
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<!doctype html>\n<html lang=\"vi\">\n<head>\n<meta charset=\"utf-8\">\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n<title>Trang Giáo Viên</title>\n<link rel=\"stylesheet\" href=\"" + req.getContextPath() + "/css/styles.css\">\n</head>\n<body>\n<nav class=\"main-menu\">\n <a href=\"" + req.getContextPath() + "/controller?action=teacher\">Giáo viên</a> |\n <a href=\"#\">Danh sách lớp</a> |\n <a href=\"#\">Tài liệu</a> |\n <a href=\"" + req.getContextPath() + "/controller?action=logout\">Đăng xuất</a>\n</nav>\n<main class=\"container\">\n <h1>Chào mừng, " + session.getAttribute("username") + "</h1>\n <section class=\"card\">\n <h2>Thông tin giáo viên</h2>\n <p>Họ tên: Nguyễn Văn A</p>\n <p>Môn dạy: Toán</p>\n </section>\n <section class=\"card\">\n <h2>Hoạt động</h2>\n <button onclick=\"alert('Tính năng demo: xem lịch\')\">Xem lịch</button>\n <button onclick=\"alert('Tính năng demo: gửi thông báo')\">Gửi thông báo</button>\n </section>\n</main>\n</body>\n</html>");
    }
}
