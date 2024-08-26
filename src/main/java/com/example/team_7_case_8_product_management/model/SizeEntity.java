package com.example.team_7_case_8_product_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "sizes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SizeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer sizeId;

    private String title;

}
