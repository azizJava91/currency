package com.home_task.repository;


import com.home_task.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByMailAndPassword(String mail, String password);
     UserEntity findByMail(String mail);
}
