package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.item.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ItemDao extends CrudRepository<Item, Long> {

    @Modifying
    @Query("delete from Item i where i.itemId = ?1")
    void deleteByItemId(Long id);

    boolean existsByItemId(Long id);

    @Query("select i from Item i where i.itemId in (?1)")
    Collection<Item> findAllByIds(Collection<Long> ids);
}
