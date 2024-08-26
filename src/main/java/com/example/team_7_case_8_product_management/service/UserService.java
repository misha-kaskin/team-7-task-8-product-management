package com.example.team_7_case_8_product_management.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.team_7_case_8_product_management.exception.IncorrectLoginException;
import com.example.team_7_case_8_product_management.exception.UserAlreadyExistsException;
import com.example.team_7_case_8_product_management.exception.UserNotExistsException;
import com.example.team_7_case_8_product_management.exception.UserNotFoundByIdException;
import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.User;
import com.example.team_7_case_8_product_management.model.UserDto;
import com.example.team_7_case_8_product_management.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static java.time.Instant.now;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final Algorithm algorithm;

    @Transactional
    public void registerUser(User userDto) {
        Optional<User> optionalUser = userDao.findByLogin(userDto.getLogin());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        String hash = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
        userDto.setPassword(hash);
        userDto.setRegisterDate(LocalDate.now());
        userDao.save(userDto);
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

    public void editUser(User user) {
        Optional<User> optionalUser = userDao.findById(user.getUserId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundByIdException();
        }

        User realUser = optionalUser.get();
        realUser.setName(user.getName());
        realUser.setRole(user.getRole());
        realUser.setBalance(user.getBalance());

        userDao.save(realUser);
    }

    public void deleteUser(User user) {
        Long userId = user.getUserId();
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundByIdException();
        }
        userDao.deleteById(userId);
    }
}
