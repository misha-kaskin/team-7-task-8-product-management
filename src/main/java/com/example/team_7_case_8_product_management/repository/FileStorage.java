package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.exception.CantCreateFileException;
import com.example.team_7_case_8_product_management.exception.CantFindFileException;
import com.example.team_7_case_8_product_management.model.item.FullItemDto;
import com.example.team_7_case_8_product_management.model.item.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class FileStorage {

    @Value("${file.storage.path}")
    private String path;

    public void saveFile(FullItemDto item) {
        String fileName = item.getType().getTypeId() + item.getProductName() + item.getItemId();
        try (FileOutputStream fos = new FileOutputStream(path + fileName)) {
            fos.write(item.getImage().getBytes());
        } catch (IOException e) {
            throw new CantCreateFileException();
        }
    }

    public String loadFile(Item item) {
        String fileName = item.getType().getTypeId() + item.getProductName() + item.getItemId();
        try (FileInputStream fis = new FileInputStream(path + fileName)) {
            byte[] bytes = fis.readAllBytes();
            String image = new String(bytes);
            return image;
        } catch (IOException e) {
//            throw new CantFindFileException();
        }
        return null;
    }
}
