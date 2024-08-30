package com.example.team_7_case_8_product_management.repository;

import com.example.team_7_case_8_product_management.model.item.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface ItemDao extends CrudRepository<Item, Long> {

    @Modifying
    @Query("delete from Item i where i.itemId = ?1")
    void deleteByItemId(Long id);

    @Query("select i from Item i where i.state.stateId = 3")
    List<Item> findAllNotDeleted();

    boolean existsByItemId(Long id);

    @Query("select i from Item i where i.itemId in (?1)")
    Collection<Item> findAllByIds(Collection<Long> ids);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "update items set state_id = 2 " +
                    "where item_id = ?1 and not exists (" +
                    "select * from warehouses where item_id = ?1 and count > 0)"
    )
    void archiveItemById(Long id);

//    @Query("select i from Item i where i.state.sateId = ?1")
//    List<Item> findAllByStateId(Long id);

}
