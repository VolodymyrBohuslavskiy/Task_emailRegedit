package com.example.task.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class jwrsService {

    public String add(String word, String kay, int timeSeconds) {
        return Jwts.builder()//Оголошуємо змінну для шифрування
                .setSubject(word)//Солво що шифрується
                .signWith(SignatureAlgorithm.HS512, kay.getBytes())//Обираємо алгоритм шифрування ,слово для дешифрування у байтовий масив
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * timeSeconds))// термін дії цього jwtoken
                .compact();// Кінець цього шифруванння
    }

    public String toNormal(String code, String kay) {
        return Jwts.parser()//Оголошуємо змінну для дешифрування
                .setSigningKey(kay.getBytes())//слово для дешифрування у байтовий масив
                .parseClaimsJws(code)//Вставити
                .getBody()//Дістаємо боді
                .getSubject();
    }
}
