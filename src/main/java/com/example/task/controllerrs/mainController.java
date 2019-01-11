package com.example.task.controllerrs;


import com.example.task.models.User;
import com.example.task.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class mainController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/hello")
    public String in() {
        return "in";
    }

    @PostMapping("/add")
    public String add(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return "login";
    }
}
