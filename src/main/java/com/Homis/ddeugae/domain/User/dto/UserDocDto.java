package com.Homis.ddeugae.domain.User.dto;

import lombok.Getter;

@Getter
public class UserDocDto {
    private final String Id;
    private final String userProfileImgUrl;
    private final String userNickname;

    public UserDocDto(String id, String nickname){
        if (id == null || nickname == null) {
            throw new IllegalArgumentException("사용자 정보 필수값 중 null값이 있습니다.");
        }

        this.Id = id;
        this.userNickname = nickname;
        this.userProfileImgUrl = null;
    }

    public UserDocDto(String id, String nickname, String profileUrl){
        if (id == null || nickname == null) {
            throw new IllegalArgumentException("사용자 정보 필수값 중 null값이 있습니다.");
        }

        this.Id = id;
        this.userNickname = nickname;
        this.userProfileImgUrl = profileUrl;
    }
}
