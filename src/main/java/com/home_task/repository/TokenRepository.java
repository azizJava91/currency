package com.home_task.repository;

import com.home_task.entity.Token;
import com.home_task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByUserEntityAndTokenAndActive(UserEntity userEntity, String token, Integer active);
}
