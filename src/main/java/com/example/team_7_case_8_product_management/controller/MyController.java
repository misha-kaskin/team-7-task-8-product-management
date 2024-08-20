package com.example.team_7_case_8_product_management.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static java.time.Instant.*;
import static org.springframework.http.HttpHeaders.*;

@Slf4j
@CrossOrigin
@RestController
public class MyController {
    private String secretKey = "abc";

    @GetMapping
    public ResponseEntity<byte[]> downloadReport(@RequestHeader(AUTHORIZATION) String token) throws IOException {
        if (!checkToken(token)) {
            throw new RuntimeException();
        }
        byte[] data =  Files.readAllBytes(Path.of("src/main/resources/image.translated.jpg"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        httpHeaders.setContentLength(data.length);
//        byte[] encode = Base64.getEncoder().encode(data);
//        String str = new String(encode);
//        System.out.println(str);
        return new ResponseEntity<>(data, httpHeaders, HttpStatus.OK);
    }

    @PostMapping
    public TokenModel createToken(@RequestBody User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = now();
        Instant exp = now.plusSeconds(60);

        return new TokenModel(JWT.create()
                .withIssuer("auth-service")
                .withAudience("bookstore")
//                .withSubject(clientId)
                .withClaim("login", user.getLogin())
                .withClaim("password", user.getPassword())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm), exp.getEpochSecond());
    }

    public boolean checkToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = JWT.decode(token);

        if (decodedJWT.getExpiresAt().before(Date.from(now()))) {
            System.out.println("Срок токена истек");
        }

        try {
            decodedJWT = verifier.verify(token);
            if (!decodedJWT.getIssuer().equals("auth-service")) {
                log.error("Issuer is incorrect");
                return false;
            }

            if (!decodedJWT.getAudience().contains("bookstore")) {
                log.error("Audience is incorrect");
                return false;
            }
        } catch (JWTVerificationException e) {
            log.error("Token is invalid: " + e.getMessage());
            return false;
        }

        return true;
    }
}
