package com.example.team_7_case_8_product_management.model.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerItems {
    private List<FullItemDto> actual;
    private List<ShortItemDto> archive;
    private List<FullItemDto> noSale;
}
