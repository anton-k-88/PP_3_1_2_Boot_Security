package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Gender;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleCrudRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {
    private UserService userService;
    private RoleCrudRepository roleCrudRepository;

    @Autowired
    public UserController(UserService userService, RoleCrudRepository roleCrudRepository) {
        this.userService = userService;
        this.roleCrudRepository = roleCrudRepository;
    }

    @GetMapping("/admin")
    public ModelAndView usersPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        modelAndView.addObject("usersList", userService.getUsersList());
        return modelAndView;
    }

    @GetMapping("/user")
    public ModelAndView userPage(@AuthenticationPrincipal User user) {
        //       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        modelAndView.addObject("user", user);
//        modelAndView.addObject("genders", Gender.values());
        return modelAndView;
    }

    @GetMapping("/edit/{userId}")
    public ModelAndView userEditPage(@PathVariable("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edituser");
        modelAndView.addObject("user", userService.getUser(userId));
        modelAndView.addObject("genders", Gender.values());
        modelAndView.addObject("roles", roleCrudRepository.findAllByRoleNameNotNull());
        return modelAndView;
    }

    @PutMapping("/edit")
    public ModelAndView userEdit(@ModelAttribute("user") User user,
                                 @RequestParam(value = "action") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        if (action.equals("save")) {
            userService.saveUser(user);
        }
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView userAddPage() {
        User newUser = new User();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adduser");
        modelAndView.addObject("newuser", newUser);
        modelAndView.addObject("genders", Gender.values());
        modelAndView.addObject("roles", roleCrudRepository.findAllByRoleNameNotNull());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView userAdd(@ModelAttribute("user") User user,
                                @RequestParam(value = "action") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        if (action.equals("save")) {
            userService.saveUser(user);
        }
        return modelAndView;
    }

    @GetMapping("/delete/{userId}")
    public ModelAndView userDeletePage(@PathVariable("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deleteuser");
        modelAndView.addObject("user", userService.getUser(userId));
        return modelAndView;
    }

    @DeleteMapping("/delete/{userId}")
    public ModelAndView userDelete(@ModelAttribute("user") User user,
                                   @ModelAttribute("userId") Long id,
                                   @RequestParam(value = "action") String action) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin");
        if (action.equals("delete")) {
            user.setId(id);
            userService.deleteUser(user);
        }
        return modelAndView;
    }

}
