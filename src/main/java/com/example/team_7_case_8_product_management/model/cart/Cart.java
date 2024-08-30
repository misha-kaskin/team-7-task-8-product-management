package com.example.team_7_case_8_product_management.model.cart;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

@Data
@Entity
@Table(name = "carts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {


    @EmbeddedId
    private EmbeddedCartId cartId;

    private Long count;

}
