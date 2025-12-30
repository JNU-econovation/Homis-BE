package com.Homis.ddeugae.domain.Make.entity;

import com.Homis.ddeugae.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Made {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "made_data_id")
    private Long madeDataId;

    @Column(nullable = false)
    private String madeName;

    @Column(nullable = false)
    private Integer madeSize;

    @Column
    private String madeImgUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String madeDetail;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "maker_data_id", referencedColumnName = "user_data_id")
    private User user;   // User.java의 userDataId와 FK
}