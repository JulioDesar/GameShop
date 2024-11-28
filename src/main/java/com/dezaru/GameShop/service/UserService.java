package com.dezaru.GameShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.dezaru.GameShop.model.User;
import com.dezaru.GameShop.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(Sort sort){
        return this.userRepository.findAll(sort);
    }

    public void save(User user) {
        this.userRepository.save(user);
    }

    public User findById(int id){
        return this.userRepository.findById(id).orElse(null);
    }

    public void delete(User user) {
        this.userRepository.delete(user);
    }

}
