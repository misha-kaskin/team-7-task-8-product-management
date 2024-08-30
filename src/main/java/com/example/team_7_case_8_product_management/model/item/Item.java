package com.example.team_7_case_8_product_management.model.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long itemId;

    private String productName;

    @ManyToOne()
    @JoinColumn(name = "type_id")
    private ItemType type;

    private String description;
    private Float price;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private ItemState state;

}
