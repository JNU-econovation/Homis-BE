package com.Homis.ddeugae.domain.Store.service;

import com.Homis.ddeugae.domain.Sale.repository.SaleItemsMapping;
import com.Homis.ddeugae.domain.Sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final SaleRepository saleRepository;

    public List<SaleItemsMapping> loadMyPagePreview(Long userDataId){
        return saleRepository.findUserItemsById(userDataId);
    }
}
