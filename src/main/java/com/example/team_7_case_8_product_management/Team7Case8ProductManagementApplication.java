package com.example.team_7_case_8_product_management;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.team_7_case_8_product_management.model.cart.Cart;
import com.example.team_7_case_8_product_management.model.order.Order;
import com.example.team_7_case_8_product_management.repository.CartDao;
import com.example.team_7_case_8_product_management.repository.OrderDao;
import kong.unirest.HttpResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;

import static kong.unirest.Unirest.get;
import static kong.unirest.Unirest.put;

@SpringBootApplication
public class Team7Case8ProductManagementApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext run = SpringApplication.run(Team7Case8ProductManagementApplication.class, args);
    }

}
