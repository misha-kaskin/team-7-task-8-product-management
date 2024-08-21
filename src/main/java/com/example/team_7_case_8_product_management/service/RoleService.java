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
import com.example.team_7_case_8_product_management.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final Algorithm algorithm;

    public void checkRole(String token, String role) {
        if (token == null) {
            throw new UserNotAuthException();
        }
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            String role1 = decodedJWT.getClaim("role").asString();
            if (role == null) {
                throw new TokenNotValidException();
            }
            if (!role.equals(role1)) {
                throw new MismatchRoleException();
            }
        } catch (JWTVerificationException e) {
            throw new TokenNotValidException();
        }
    }

}
