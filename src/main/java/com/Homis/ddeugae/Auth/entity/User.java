package com.Homis.ddeugae.Auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDataId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String userPassword;

    @Column(nullable = false, unique = true)
    private String userNickname;

//    @Column
//    private String refreshToken;

    @Column
    private String userProfileImgUrl;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
