package com.dezaru.GameShop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dezaru.GameShop.DTO.user.UserDto;
import com.dezaru.GameShop.DTO.user.UserEdit;
import com.dezaru.GameShop.DTO.user.UserEditDto;
import com.dezaru.GameShop.DTO.user.UserListDto;
import com.dezaru.GameShop.model.User;
import com.dezaru.GameShop.model.Enum.Role;
import com.dezaru.GameShop.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @GetMapping({"", "/"})
    public String showAdminPage(Model model) {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<UserListDto> userList = UserListDto.from(users);
        model.addAttribute("users", userList);

        return "admin/index";
    }

    @GetMapping("/create")
    public String showCreateUsersPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "admin/create";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute UserDto userDto, BindingResult result) {
        if (result.hasErrors())
            return "admin/create";

        User user = new User(userDto.name(), userDto.email(), userDto.password(), Role.valueOf(userDto.role()));
        userRepository.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            Optional<User> u = userRepository.findById(id);

            if(u.isPresent()){
                User user = u.get();
                model.addAttribute("user", user);

                UserEditDto userDto = new UserEditDto(user.getName(), user.getEmail(), user.getPassword(), user.getRole().toString(), user.getIsActive());
                model.addAttribute("userDto", userDto);
            }
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/products";
        }
        return "admin/edit";
    }
}
