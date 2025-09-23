package com.school.school_management.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/controller")
public class MainController extends HttpServlet {

    private static final long serialVersionUID = 1L;

// Hard-coded users: admin, student, teacher all have password 12345
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("login".equals(action)) {
            handleLogin(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }
        switch (action) {
            case "teacher" -> // redirect to TeacherServlet
                resp.sendRedirect(req.getContextPath() + "/teacher");
            case "logout" -> {
                HttpSession session = req.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                resp.sendRedirect(req.getContextPath() + "/login.html");
            }
            default -> resp.sendRedirect(req.getContextPath() + "/login.html");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null) {
            req.setAttribute("error", "Vui lòng nhập tài khoản và mật khẩu.");
            req.getRequestDispatcher("/login.html").forward(req, resp);
            return;
        }

// simple hard-coded auth
        if (password.equals("12345") && (username.equals("admin") || username.equals("student") || username.equals("teacher"))) {
            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);
            String role = username.equals("admin") ? "ADMIN" : (username.equals("teacher") ? "TEACHER" : "STUDENT");
            session.setAttribute("role", role);
// after login, go to a controller action or default page
            if (role.equals("TEACHER")) {
                resp.sendRedirect(req.getContextPath() + "/controller?action=teacher");
            } else {
// for simplicity, other roles return to a simple landing page (reuse teacher page for demo)
                resp.getWriter().println("<html><body><h2>Xin chào " + username + " (" + role + ")</h2><p>Trang demo cho role: " + role + "</p><p><a href='" + req.getContextPath() + "/controller?action=logout'>Đăng xuất</a></p></body></html>");
            }
        } else {
// authentication failed
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().println("<html><body><h3>Đăng nhập thất bại. Tài khoản hoặc mật khẩu không đúng.</h3><p><a href='" + req.getContextPath() + "/login.html'>Quay lại</a></p></body></html>");
        }
    }
}
