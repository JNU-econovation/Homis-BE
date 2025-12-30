package com.Homis.ddeugae.domain.Make.service;

import com.Homis.ddeugae.domain.Make.dto.MadeUploadReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MakeService {
    public Long uploadMadeDesign(MadeUploadReq uploadReq){
        LocalDateTime requested_at = LocalDateTime.now(); // 타임스탬프 안 찍는 대신 요청 시점 기준으로 기록
        
        if (uploadReq.getMadeName() == null){ // 도안명 없으면 생성일로
            String made_name = requested_at.toString().formatted("yyyy-MM-dd"); 
        }
        
        // 이 아래는 논의 끝난 후에 채워야 함
    }
}
