package com.Homis.ddeugae.domain.Store.dto;

import com.Homis.ddeugae.domain.Sale.repository.SaleItemsMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class StoreLoadResp {
    List<SaleItemsMapping> salerStoreItems;
    boolean myStore;
}
