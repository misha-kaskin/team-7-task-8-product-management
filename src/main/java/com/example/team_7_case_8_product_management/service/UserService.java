package com.example.team_7_case_8_product_management.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.team_7_case_8_product_management.exception.*;
import com.example.team_7_case_8_product_management.model.TokenModel;
import com.example.team_7_case_8_product_management.model.cart.CartItemDto;
import com.example.team_7_case_8_product_management.model.cart.SizeDto;
import com.example.team_7_case_8_product_management.model.user.*;
import com.example.team_7_case_8_product_management.repository.UserDao;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static java.time.Instant.now;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final Algorithm algorithm;
    private final CartService cartService;

    public void registerUser(UserDto userDto) {
        Optional<User> optionalUser = userDao.findByLogin(userDto.getLogin());
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        String hash = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
        User user = User.builder()
                .name(userDto.getName())
                .login(userDto.getLogin())
                .password(hash)
                .role(userDto.getRole())
                .registerDate(LocalDate.now())
                .balance(userDto.getBalance())
                .build();
        userDao.save(user);
    }

    public TokenModel loginUser(LoginUserDto user) {
        Optional<User> optionalUser = userDao.findByLogin(user.getLogin());
        if (optionalUser.isEmpty()) {
            throw new UserNotExistsException();
        }
        User realUser = optionalUser.get();
        if (!BCrypt.checkpw(user.getPassword(), realUser.getPassword())) {
            throw new IncorrectLoginException();
        }
        return tokenFromUser(realUser);
    }

    public TokenModel refreshToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Long userId = decodedJWT.getClaim("userId").asLong();
            if (userId == null) {
                throw new UserNotFoundByIdException();
            }
            Optional<User> optionalUser = userDao.findById(userId);
            if (optionalUser.isEmpty()) {
                throw new UserNotFoundByIdException();
            }
            return tokenFromUser(optionalUser.get());
        } catch (JWTVerificationException e) {
            throw new TokenNotValidException();
        }
    }

    public Iterable<GetUserDto> getUsers() {
        return userDao.findAll().stream()
                .map(u -> GetUserDto.builder()
                        .userId(u.getUserId())
                        .name(u.getName())
                        .login(u.getLogin())
                        .role(u.getRole())
                        .registerDate(u.getRegisterDate())
                        .balance(u.getBalance())
                        .build())
                .toList();
    }

    public void editUser(EditUserDto editUserDto) {
        Optional<User> optionalUser = userDao.findById(editUserDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundByIdException();
        }

        User realUser = optionalUser.get();
        realUser.setName(editUserDto.getName());
        realUser.setRole(editUserDto.getRole());
        realUser.setBalance(editUserDto.getBalance());

        userDao.save(realUser);
    }

    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundByIdException();
        }
        userDao.deleteById(userId);
    }

    private TokenModel tokenFromUser(User user) {
        Instant now = now();
        Instant exp = now.plusSeconds(1_000_000_000);
        String newToken = JWT.create()
                .withClaim("userId", user.getUserId())
                .withClaim("role", user.getRole())
                .withClaim("name", user.getName())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm);
        return new TokenModel(newToken, exp.getEpochSecond());
    }

    public GetUserDto getUserById(Long id) {
        if (id == null) {
            throw new UserNotFoundByIdException();
        }
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundByIdException();
        }
        User user = optionalUser.get();
        Collection<CartItemDto> items = cartService.getItemsByUserId(id).getItems();
        Long count = 0l;
        for (CartItemDto item : items) {
            for (SizeDto size : item.getSizes()) {
                Long cnt = size.getCount();
                count += cnt;
            }
        }

        return GetUserDto.builder()
                .userId(user.getUserId())
                .login(user.getLogin())
                .balance(user.getBalance())
                .registerDate(user.getRegisterDate())
                .name(user.getName())
                .cartCount(count)
                .role(user.getRole())
                .build();
    }
}
