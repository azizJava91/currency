package com.home_task.repository;


import com.home_task.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository  extends JpaRepository<CurrencyEntity, Long> {
      CurrencyEntity findByCode(String code);
}
