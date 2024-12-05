package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserService {
    void save(User user);

    List<User> read();

    User getUserByName(String name);

    void delete(long id);

    User update(long id, String name, String lastname, String password, Set<Role> role);

    User upPage(long id);

}
