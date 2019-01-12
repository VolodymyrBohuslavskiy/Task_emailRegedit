package com.example.task.controllerrs;

import com.example.task.models.User;
import com.example.task.services.UserService;
import com.example.task.services.emailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import java.util.Date;

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
        String code = user.getUsername();
        user.setSecretCode(code);
        userService.save(user);

        String sendcode = Jwts.builder()//Оголошуємо змінну для шифрування
                .setSubject(code)//Солво що шифрується
                .signWith(SignatureAlgorithm.HS512, "spring".getBytes())//Обираємо алгоритм шифрування ,слово для дешифрування у байтовий масив
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 30))// термін дії цього jwtoken
                .compact();// Кінець цього шифруванння

        System.out.println("http://localhost:8080/authorization/" + sendcode);
        emailService.send(user.getEmail(), "http://localhost:8080/authorization/" + sendcode);
        return "login";
    }

    @GetMapping("/authorization/{code}")
    public String authorization(@PathVariable String code) {
        try {
            code = Jwts.parser()//Оголошуємо змінну для дешифрування
                    .setSigningKey("spring".getBytes())//слово для дешифрування у байтовий масив
                    .parseClaimsJws(code)//Вставити
                    .getBody()//Дістаємо боді
                    .getSubject();
            User user = userService.findBySecretCode(code);
            user.setEnabled(true);
            userService.save(user);
        } finally {
            return "login";
        }
    }

}
