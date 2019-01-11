package com.example.task.controllerrs;


import com.example.task.models.User;
import com.example.task.services.UserService;
import com.example.task.services.emailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;

@Controller
public class mainController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private emailService emailService;


    @GetMapping("/")
    public String home() {
        return "index";
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
    public String add(User user) throws MessagingException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        emailService.send(user.getEmail(), user.getUsername());
        return "login";
    }

    @GetMapping("/authorization-{id}")
    public String authorization(@PathVariable String id) {
        return "";
    }

}
