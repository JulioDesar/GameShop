package com.dezaru.GameShop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dezaru.GameShop.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    Page findAll(Pageable pageable);
}
