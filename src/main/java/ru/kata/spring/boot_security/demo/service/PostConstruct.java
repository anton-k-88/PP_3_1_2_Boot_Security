package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Gender;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleCrudRepository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Component
public class PostConstruct {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleCrudRepository roleCrudRepository;

    @javax.annotation.PostConstruct
    public void init() {

        Role roleAdmin = new Role("ROLE_ADMIN");
        roleCrudRepository.save(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleCrudRepository.save(roleUser);

        List<Role> list = roleCrudRepository.findAllByRoleNameNotNull();
        for (Role role : list) {
            System.out.println(role.getRoleName());
        }

        List<Role> roleSet1 = new ArrayList<>();
        roleSet1.add(roleAdmin);
        roleSet1.add(roleUser);

        List<Role> roleSet2 = new ArrayList<>();
        roleSet2.add(roleUser);

        List<Role> roleSet3 = new ArrayList<>();
        roleSet3.add(roleUser);

        List<Role> roleSet4 = new ArrayList<>();
        roleSet4.add(roleUser);

        List<Role> roleSet5 = new ArrayList<>();
        roleSet5.add(roleUser);


        userService.saveUser(new User("Иван", "Петров", Gender.male, "+79050987654", "ivan@mail.ru", "ivan", "ivan", roleSet1));
        userService.saveUser(new User("Петр", "Ольгин", Gender.male, "+79031234567", "petr@mail.ru", "petr", "petr", roleSet2));
        userService.saveUser(new User("Ольга", "Игорева", Gender.female, "+79265545544", "olga@mail.ru", "olga", "olga", roleSet3));
        userService.saveUser(new User("Игорь", "Алимов", Gender.male, "+79234446688", "igor@mail.ru", "igor", "igor", roleSet4));
        userService.saveUser(new User("Алина", "Аревадзе", Gender.female, "+74957772211", "alina@mail.ru", "alina", "alina", roleSet5));

    }


}
