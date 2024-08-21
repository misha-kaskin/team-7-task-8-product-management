package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemDao extends CrudRepository<Item, Long> {
}
