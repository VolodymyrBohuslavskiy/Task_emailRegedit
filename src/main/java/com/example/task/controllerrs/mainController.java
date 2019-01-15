package com.example.task.controllerrs;

import com.example.task.models.User;
import com.example.task.services.UserService;
import com.example.task.services.emailService;
import com.example.task.services.jwrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class mainController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private emailService emailService;
    @Autowired
    private jwrsService jwrsService;

    @PostMapping("/hello")
    public String in() {
        return "in";
    }

    @PostMapping("/add")
    public String add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        try {
            emailService.send(user.getEmail(), "http://localhost:8080/authorization/" + jwrsService.add(user.getEmail(), "s", 30));
        } catch (Exception e) {
            System.out.println("http://localhost:8080/authorization/" + jwrsService.add(user.getEmail(), "s", 30));
        }
        return "CheckEmail";
    }

    @GetMapping("/authorization/{code}")
    public String authorization(@PathVariable String code) {
        try {
            User user = userService.findByEmail(jwrsService.toNormal(code, "s"));
            user.setEnabled(true);
            userService.save(user);
            if (user.isEnabled()) return "autoriz";
        } catch (Exception e) {
            return "autorizFail";
        }
        return "index";
    }

}
