package com.example.team_7_case_8_product_management.model.order;

import com.example.team_7_case_8_product_management.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "order_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus status;

    private LocalDate orderDate;
    private String firstName;
    private String secondName;
    private String lastName;
    private String address;

}
