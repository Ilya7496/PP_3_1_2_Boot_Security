package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
    public String create(@ModelAttribute("create_user") User user,
                         @RequestParam("roles") Set<Long> selectById) {
        if(user != null) {
            Set<Role> roles = new HashSet<>();
            for (Long newRole : selectById) {
                roles.addAll(roleService.getRoleById(newRole));
            }
            user.setRoles(roles);
            userService.save(user);
        }
        return "redirect:/admin/allusers";
    }

    @GetMapping("/user/new")
    public String newUser(@ModelAttribute("newUser") User user, Model model) {
        Set<Role> rolesList = new HashSet<>(roleService.getAllRoles());
        model.addAttribute("list_role", rolesList);
        return "new_user";
    }

    @GetMapping("/allusers")
    public String read(Model model) {
        model.addAttribute("get_user", userService.read());
        return "user_show";
    }

    @GetMapping("/user/delete/YES")
    public String deleteUser(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin/allusers";
    }

    @GetMapping("/user/delete")
    public String pageDelete(@RequestParam("id") long id, Model model) {
        model.addAttribute("que", userService.upPage(id));
        return "delete_user";
    }

    @GetMapping("/user/update")
    public String pageUpdate(@RequestParam("id") long id, Model model) {
        model.addAttribute("up_user", userService.upPage(id));
        model.addAttribute("update_role", new HashSet<>(roleService.getAllRoles()));
        return "update_user";
    }
    @PostMapping("/user/edit")
    public String update(@ModelAttribute("User") User user,
                         @RequestParam("id") long id,
                         @RequestParam("name") String name,
                         @RequestParam("lastName") String lastname,
                         @RequestParam("password") String password,
                         @RequestParam("role") Set<Long> roleById) {
        Set<Role> roles = new HashSet<>();
        for (Long upRole : roleById) {
            roles.addAll(roleService.getRoleById(upRole));
        }
        user.setRoles(roles);
        userService.update(id, name, lastname, password, roles);
        return "redirect:/admin/allusers";
    }

    @GetMapping("/user")
    public String userPageAdmin(@RequestParam("id") long id, Model model) {
        model.addAttribute("user_page", userService.upPage(id));
        return "user_page";
    }


}