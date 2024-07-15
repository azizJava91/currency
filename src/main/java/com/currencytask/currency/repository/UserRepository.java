package com.currencytask.currency.repository;

import com.currencytask.currency.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByMailAndPassword(String mail, String password);

}
