package com.school.school_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // login.html trong templates
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          Model model) {

        // check cứng account
        if (("admin".equals(username) || "student".equals(username) || "teacher".equals(username))
                && "12345".equals(password)) {

            if (null == username) {
                return "student"; // student.html
            } else return switch (username) {
                case "admin" -> "admin";
                case "teacher" -> "teacher";
                default -> "student";
            }; // admin.html
            // teacher.html
            // student.html
            

        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "login";
        }
    }
}
