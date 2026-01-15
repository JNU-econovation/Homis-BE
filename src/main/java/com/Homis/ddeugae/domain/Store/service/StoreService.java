package com.Homis.ddeugae.domain.Store.service;

import com.Homis.ddeugae.domain.Sale.repository.SaleItemsMapping;
import com.Homis.ddeugae.domain.Sale.repository.SaleRepository;
import com.Homis.ddeugae.domain.Store.dto.StoreLoadResp;
import com.Homis.ddeugae.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    public List<SaleItemsMapping> loadMyPagePreview(Long userDataId){
        return saleRepository.findUserItemsById(userDataId);
    }

    public StoreLoadResp loadStorePreview(Long userDataId, String salerNickname){
        if(salerNickname.equals(userRepository.findById(userDataId).get().getUserNickname())){
            // 내 스토어임.
            return new StoreLoadResp(saleRepository.findUserItemsById(userDataId),
                    userRepository.getUserProfileById(userDataId), true);
        }

        return new StoreLoadResp(saleRepository.findUserItemsByNickname(salerNickname),
                userRepository.getUserProfileById(userDataId), false);
    }
}
