package com.Homis.ddeugae.domain.User.repository;

import com.Homis.ddeugae.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserNickname(String userNickname);
}