package com.example.team_7_case_8_product_management.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.team_7_case_8_product_management.exception.IncorrectLoginException;
import com.example.team_7_case_8_product_management.exception.UserAlreadyExistsException;
import com.example.team_7_case_8_product_management.exception.UserNotExistsException;
import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.User;
import com.example.team_7_case_8_product_management.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static java.time.Instant.now;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final Algorithm algorithm;

    @Transactional
    public void registerUser(User user) {
        Optional<User> optionalUser = userDao.findByLogin(user.getLogin());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hash);
        User save = userDao.save(user);
    }

    @Transactional
    public TokenModel loginUser(User user) {
        Optional<User> optionalUser = userDao.findByLogin(user.getLogin());
        if (optionalUser.isEmpty()) {
            throw new UserNotExistsException();
        }
        User realUser = optionalUser.get();
        if (!BCrypt.checkpw(user.getPassword(), realUser.getPassword())) {
            throw new IncorrectLoginException();
        }

        Instant now = now();
        Instant exp = now.plusSeconds(1_000_000_000);
        String token = JWT.create()
                .withClaim("userId", realUser.getUserId())
                .withClaim("role", realUser.getRole())
                .withClaim("name", realUser.getName())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm);

        return new TokenModel(token, exp.getEpochSecond());
    }

    public Iterable<User> getUsers() {
        return userDao.findAll();
    }

}
