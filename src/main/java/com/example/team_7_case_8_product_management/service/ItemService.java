package com.example.team_7_case_8_product_management.service;

import com.example.team_7_case_8_product_management.model.Item;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class ItemService {
    @SneakyThrows
    public void addItem(Item item) {
        String file = item.getImage();
        String fileName = item.getProductName() + item.getItemId();
        FileOutputStream fos = new FileOutputStream("src/main/resources/images/" + fileName);
        fos.write(Base64.getDecoder().decode(file));
        fos.close();
    }
}
