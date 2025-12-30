package com.Homis.ddeugae.domain.Make.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class HtmlRenderService {

    private final RestClient restClient;

    public HtmlRenderService(RestClient.Builder builder){
        this.restClient = builder.build();
    }

    private static record WebSnapResponse(String status, String image_url) {}

    /**
     * WebSnapAPI 호출 후 image_url을 반환
     *
     * @param targetUrl : 프론트에서 요청하며 보낸 preview URL
     * @param authToken : WebSnapAPI 인증 토큰
     * @return : WebSnapAPI가 생성한 PNG 이미지 URL
     */
    public String captureDesign(String targetUrl, String authToken){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https").host("websnapapi.com").path("/v1/")
                        .queryParam("auth", authToken)
                        .queryParam("url", targetUrl)
                        .queryParam("fullsize", true) //웹 페이지 전체 스크린샷
                        .build()
                ).retrieve().body(WebSnapResponse.class)
                .image_url(); // 반환된 이미지 url
    }
}
