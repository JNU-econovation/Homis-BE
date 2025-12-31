package com.Homis.ddeugae.domain.Make.service;

import com.Homis.ddeugae.common.exception.CustomException;
import com.Homis.ddeugae.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class WebSnapAPIService {

    private final RestClient restClient;

    public WebSnapAPIService(RestClient.Builder builder){
        this.restClient = builder.build();
    }

    private record WebSnapResponse(String status, String image_url) {}

    /**
     * WebSnapAPI 호출 후 image_url을 반환
     *
     * @param targetUrl : 프론트에서 요청하며 보낸 preview URL
     * @param authToken : WebSnapAPI 인증 토큰
     * @return : WebSnapAPI가 생성한 PNG 이미지 URL
     */
    public String captureDesign(String targetUrl, String authToken){
        try{
            String webSnapRespdata = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https").host("websnapapi.com").path("/v1/")
                            .queryParam("auth", authToken)
                            .queryParam("url", targetUrl)
                            .queryParam("fullsize", true) //웹 페이지 전체 스크린샷
                            .build()
                    ).retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            (req, res) -> {
                                String body = res.getBody().toString();
                                HttpStatusCode status = res.getStatusCode();

                                log.error("WebSnap API 에러: status={}, body={}", status, body);

                                throw new CustomException(ErrorCode.FAILED_WEBSNAPSHOT_API);
                            }
                    )
                    .body(WebSnapResponse.class)
                    .image_url(); // 반환된 이미지 url

            return webSnapRespdata;
        } catch (Exception e){
            throw new CustomException(ErrorCode.FAILED_WEBSNAPSHOT_API, e);
        }
    }
}
