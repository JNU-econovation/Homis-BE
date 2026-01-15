package com.Homis.ddeugae.domain.User.repository;

import com.Homis.ddeugae.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserNickname(String userNickname);

    @Query(value = "SELECT user_nickname, user_profile_img_url FROM user WHERE(user_data_id=:user_data_id)"
            , nativeQuery = true)
    UserProfileMapping getUserProfileById(@Param(value = "user_data_id") Long userDataId);
}