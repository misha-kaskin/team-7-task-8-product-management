package com.example.team_7_case_8_product_management.test_model;

import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
//@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedCartId2 implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinColumn(name = "item_id")
    private List<Item> item;

}
