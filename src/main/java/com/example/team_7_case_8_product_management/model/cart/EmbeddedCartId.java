package com.example.team_7_case_8_product_management.model.cart;

import com.example.team_7_case_8_product_management.model.item.Item;
import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.user.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedCartId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private SizeEntity size;

}
