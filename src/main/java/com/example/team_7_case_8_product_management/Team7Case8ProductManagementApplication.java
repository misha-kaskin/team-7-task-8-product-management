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
        OrderDao orderDao = (OrderDao) run.getBean("orderDao");
        CartDao cartDao = (CartDao) run.getBean("cartDao");
        Iterable<Cart> all = cartDao.findAll();
        System.out.println(all);
    }

//    static void download() {
//        String path = "11.jpg";
//        String token = "OAuth y0_AgAAAAA0tu-XAADLWwAAAAEOFFxoAAD7zU7RysNLZr7Qt11XN0xWciUWzw";
//
//        HttpResponse<String> response = get("https://cloud-api.yandex.net/v1/disk/resources/download?path=" + path)
//                .header("Authorization", token)
//                .asString();
//        String href = getUrl(response.getBody());
//        byte[] body = get(href)
//                .asBytes()
//                .getBody();
//
//        System.out.println(body.length);
//    }
//
//    static void upload() throws IOException {
//        String path = "test/text.jpg";
//        String overwrite = "true";
//        String token = "OAuth y0_AgAAAAA0tu-XAADLWwAAAAEOFFxoAAD7zU7RysNLZr7Qt11XN0xWciUWzw";
//
//        HttpResponse<String> response = get("https://cloud-api.yandex.net/v1/disk/resources/upload?path=" + path + "&overwrite=" + overwrite)
//                .header("Authorization", token)
//                .asString();
//        String href = getUrl(response.getBody());
//        response = put(href)
//                .header("Content-Type", "image/jpeg")
//                .body(Files.readAllBytes(Path.of("src/main/resources/image.translated.jpg")))
//                .asString();
//    }
//
//    static String getUrl(String body) {
//        String[] split = body.replaceAll("[\"{}]", "").split(",");
//        String href = "";
//        for (String s : split) {
//            if (s.startsWith("href")) {
//                href = s.substring(5);
//                break;
//            }
//        }
//        return href;
//    }
//
//    static void test() {
//        Algorithm algorithm = Algorithm.HMAC256("abc");
//        Instant now = Instant.now();
//        Instant exp = now.plusSeconds(15);
//
//
//        String token = JWT.create()
//                .withIssuer("auth-service")
//                .withAudience("bookstore")
//                .withSubject("")
//                .withClaim("admin", true)
//                .withIssuedAt(Date.from(now))
//                .withExpiresAt(Date.from(exp))
//                .sign(algorithm);
//
//        JWTVerifier verifier = JWT.require(algorithm).build();
//        DecodedJWT decodedJWT = verifier.verify(token);
//        boolean isAdmin = decodedJWT.getClaim("admin").asBoolean();
//        System.out.println(isAdmin);
//    }

}
