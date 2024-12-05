package com.dezaru.GameShop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Page;

import com.dezaru.GameShop.model.User;
import com.dezaru.GameShop.model.DTO.user.UserDto;
import com.dezaru.GameShop.model.DTO.user.UserEditDto;
import com.dezaru.GameShop.model.DTO.user.UserListDto;
import com.dezaru.GameShop.model.Enum.Role;
import com.dezaru.GameShop.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping({ "", "/" })
    public String showAdminPage(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        Page<UserListDto> userPage = userService.findPaginated(page, size);
        model.addAttribute("users", userPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());

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
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            User user = userService.findById(id);

            if (user == null)
                return "redirect:/admin";

            UserEditDto userDto = new UserEditDto(
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole().toString(),
                    user.getIsActive());
            model.addAttribute("user", user);
            model.addAttribute("userDto", userDto);

        } catch (Exception ex) {
            System.err.println("Exception: " + ex.getMessage());
            return "redirect:/admin";
        }
        return "admin/edit";
    }

    @PostMapping("/edit")
    public String editUser(@Valid @ModelAttribute UserEditDto userDto, @RequestParam int id, BindingResult result) {

        if (result.hasErrors())
            return "admin/create";

        try {
            User user = userService.findById(id);

            if (user == null)
                return "redirect:/admin";

            user.setName(userDto.name());
            user.setEmail(userDto.email());
            user.setPassword(userDto.password().isEmpty() ? user.getPassword() : userDto.password());
            user.setRole(Role.valueOf(userDto.role()));
            user.setIsActive(userDto.isActive());

            userService.save(user);

            return "redirect:/admin";
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/admin";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        User user = userService.findById(id);

        if (user != null) {
            userService.delete(user);
            return "redirect:/admin";
        }

        return "redirect:/admin";
    }
}
