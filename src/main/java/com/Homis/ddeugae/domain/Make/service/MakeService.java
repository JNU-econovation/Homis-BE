package com.Homis.ddeugae.domain.Make.service;

import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.exception.ErrorCode;
import com.Homis.ddeugae.domain.Make.dto.MadeDto;
import com.Homis.ddeugae.domain.Make.dto.MadeUploadReq;
import com.Homis.ddeugae.domain.Make.entity.Made;
import com.Homis.ddeugae.domain.Make.repository.MadeDetailMapping;
import com.Homis.ddeugae.domain.Make.repository.MadePreviewMapping;
import com.Homis.ddeugae.domain.Make.repository.MadeRepository;
import com.Homis.ddeugae.domain.User.entity.User;
import com.Homis.ddeugae.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MakeService {
    private final MadeDesignImageService madeDesignImageService;
    private final UserRepository userRepository;
    private final MadeRepository madeRepository;
    private final MadePdfService madePdfService;

    public void uploadMadeDesign(MadeUploadReq uploadReq, Long userDataId) {
        // 존재하는 사용자인지 확인
        if (userRepository.findById(userDataId).isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        final User userDoc = userRepository.findById(userDataId).get();

        // 타임스탬프 안 찍는 대신 요청 시점 기준으로 기록
        LocalDateTime requested_at = LocalDateTime.now();

        // 도안명 지정 안했으면 생성일로 채움 : "yyyy-MM-dd"
        String made_name = !(uploadReq.getMadeName() == null || uploadReq.getMadeName().isBlank()) ? uploadReq.getMadeName()
                : (requested_at.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 작성도안");

        // 페이지 url -> 이미지 url -> 다운로드 -> blob storage 업로드 -> url (db 저장 예정)
        String target_url = uploadReq.getDesignPreviewUrl();
        String design_image_url = madeDesignImageService.createAndStoreImage(target_url);

        // MadeDto로 문제되는 값 없는지 중간 점검
        Integer size = uploadReq.getSize();
        MadeDto check = new MadeDto(made_name, size, design_image_url);

        // 도안 제작 내용 중간 저장
        Made.MadeBuilder builder = Made.builder()
                .madeName(made_name)
                .madeSize(size)
                .user(userDoc)
                .madeImgUrl(design_image_url)
                .createdAt(requested_at);

        // 상세 스크립트는 없을 수 있음 -> 값 존재 여부에 따라 build 내용 달라짐
        if (uploadReq.getScript() != null || !uploadReq.getScript().isBlank()) { // 상세 스크립트가 있을 경우
            String made_detail = uploadReq.getScript();
            String design_pdf_url = madePdfService.createAndStorePdf(design_image_url, made_detail);

            builder.madeDetail(made_detail);
            builder.madePdfUrl(design_pdf_url);
        } else { // 상세 스크립트 없음
            String design_pdf_url = madePdfService.createAndStorePdfExcludeDetail(design_image_url);
            builder.madePdfUrl(design_pdf_url);
        }

        Made made = builder.build();
        madeRepository.save(made);
    }

    public List<MadePreviewMapping> getMadePreview(Long userDataId) {
        // 존재하는 사용자인지 확인
        if (userRepository.findById(userDataId).isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }

        return madeRepository.findAllByMakerDataId(userDataId);
    }

    public List<MadeDetailMapping> getMadeDetail(Long madeDataId) {
        // 존재하는 도안 게시글인지 확인
        if (madeRepository.findById(madeDataId).isEmpty()){
            // 에러 던지기
        }

        return madeRepository.findDetailByMadeDataId(madeDataId);
    }
}