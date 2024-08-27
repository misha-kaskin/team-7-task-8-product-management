package com.example.team_7_case_8_product_management.model.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerItems {
    private Set<FullItemDto> actual;
    private Set<ShortItemDto> archive;
    private Set<FullItemDto> noSale;
}
