package com.example.team_7_case_8_product_management.model.order;

import com.example.team_7_case_8_product_management.model.SizeEntity;
import com.example.team_7_case_8_product_management.model.item.Item;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.io.Serializable;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedOrderId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderInfo order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private SizeEntity size;

}
