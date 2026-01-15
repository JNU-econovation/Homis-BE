package com.Homis.ddeugae.domain.Store.dto;

import com.Homis.ddeugae.domain.Sale.repository.SaleItemsMapping;
import com.Homis.ddeugae.domain.User.repository.UserProfileMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class StoreMyPageResp {
    List<SaleItemsMapping> userStoreItems;
    UserProfileMapping userProfile;
}
