package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.item.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ItemDao extends CrudRepository<Item, Long> {
    void deleteByItemId(Long id);

    boolean existsByItemId(Long id);

    @Query("select i from Item i where i.itemId in (?1)")
    Collection<Item> findAllByIds(Collection<Long> ids);
}
