package com.example.team_7_case_8_product_management.model.warehouse;

import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.item.Item;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
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
public class EmbeddedWarehouseId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item itemId;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private SizeEntity size;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private WarehouseStatus status;

}
