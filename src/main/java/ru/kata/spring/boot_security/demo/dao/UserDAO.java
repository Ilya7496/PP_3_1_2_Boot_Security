package ru.kata.spring.boot_security.demo.dao;




import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UserDAO {


    void save(User user);

    List<User> read();

    User update(long id, String name, String lastname, String password, Set<Role> role);

    void delete(long id);

    User getUserByName(String name);

    User upPage(long id);
}
