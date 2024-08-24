package com.example.team_7_case_8_product_management.model.order;

import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.User;
import com.example.team_7_case_8_product_management.model.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "size_order",
            joinColumns = { @JoinColumn(name = "size_id") },
            inverseJoinColumns = { @JoinColumn(name = "order_id") }
    )
    private Set<SizeEntity> size;

    private String firstName;
    private String secondName;
    private String lastName;
    private String address;
    private LocalDate orderDate;
    private String status;
    private Long count;

}
