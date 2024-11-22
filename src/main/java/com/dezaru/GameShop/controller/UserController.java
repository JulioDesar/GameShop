package com.dezaru.GameShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dezaru.GameShop.DTO.UserListDto;
import com.dezaru.GameShop.model.User;
import com.dezaru.GameShop.repository.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping({"", "/"})
    public String showAdminPage(Model model) {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        System.out.println(users);
        List<UserListDto> userList = UserListDto.from(users);
        model.addAttribute("users", users);
        return "admin/index";
    }
}
