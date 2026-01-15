package com.Homis.ddeugae.domain.Store.service;

import com.Homis.ddeugae.common.enumType.ErrorCode;
import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.domain.Sale.repository.SaleItemsMapping;
import com.Homis.ddeugae.domain.Sale.repository.SaleRepository;
import com.Homis.ddeugae.domain.Store.dto.StoreLoadResp;
import com.Homis.ddeugae.domain.Store.dto.StoreMyPageResp;
import com.Homis.ddeugae.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    public StoreMyPageResp loadMyPagePreview(Long userDataId){
        return new StoreMyPageResp(
                saleRepository.findUserItemsById(userDataId),
                userRepository.getUserProfileById(userDataId));
    }

    public StoreLoadResp loadStorePreview(Long userDataId, String salerNickname){
        if(salerNickname.equals(userRepository.findById(userDataId).get().getUserNickname())){
            // 내 스토어임.
            return new StoreLoadResp(saleRepository.findUserItemsById(userDataId),
                    userRepository.getUserProfileById(userDataId), true);
        }

        if(userRepository.findByUserNickname(salerNickname).isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND_SALER); // 존재하지 않는 사용자의 닉네임
        }

        return new StoreLoadResp(saleRepository.findUserItemsByNickname(salerNickname),
                userRepository.getUserProfileByNickname(salerNickname), false);
    }
}
