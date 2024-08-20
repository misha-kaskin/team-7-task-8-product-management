package com.example.team_7_case_8_product_management.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

import static java.time.Instant.now;

@RestController
public class AuthController {
    private String secretKey = "abc";

    @PostMapping("lalal")
    public TokenModel getToken(@RequestBody User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = now();
        Instant exp = now.plusSeconds(60);

        String token = JWT.create()
                .withIssuer("auth-service")
                .withAudience("bookstore")
//                .withSubject(clientId)
                .withClaim("login", user.getLogin())
                .withClaim("password", user.getPassword())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm);

        return new TokenModel(token, exp.getEpochSecond());
    }
}
