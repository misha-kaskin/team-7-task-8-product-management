package com.example.team_7_case_8_product_management.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.team_7_case_8_product_management.exception.InvalidUserRoleException;
import com.example.team_7_case_8_product_management.exception.MismatchRoleException;
import com.example.team_7_case_8_product_management.exception.TokenNotValidException;
import com.example.team_7_case_8_product_management.exception.UserNotAuthException;
import com.example.team_7_case_8_product_management.model.user.User;
import com.example.team_7_case_8_product_management.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${auth.role.admin}")
    private String admin;

    @Value("${auth.role.user}")
    private String user;

    @Value("${auth.role.manager}")
    private String manager;

    private final UserDao userDao;
    private final Algorithm algorithm;

    public void validateToken(String token, String path) {
        if (token == null) {
            throw new UserNotAuthException();
        }

        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            Long userId = decodedJWT.getClaim("userId").asLong();
            String role = decodedJWT.getClaim("role").asString();
            if (userId == null || role == null) {
                throw new TokenNotValidException();
            }

            List<String> roles = List.of(admin, user, manager);
            String userRole = null;
            for (String s : roles) {
                if (path.contains("V1/API/" + s)) {
                    userRole = s;
                }
            }
            if (userRole != null) {
                if (!userRole.equals(role)) {
                    throw new MismatchRoleException();
                }
            }

            Optional<User> optionalUser = userDao.findByUserIdAndRole(userId, role);
            if (optionalUser.isEmpty()) {
                throw new InvalidUserRoleException();
            }
        } catch (JWTVerificationException e) {
            throw new TokenNotValidException();
        }
    }
}
