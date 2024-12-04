package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {


    private final UserDAO userDao;

    private final RoleDAO roleDao;
    public UserServiceImp(UserDAO userDao, RoleDAO roleDao) {this.userDao = userDao;

        this.roleDao = roleDao;
    }

    @PostConstruct
    public void firstUser() {

        Set<Role> roles = new HashSet<>();
        Role adminRole = new Role("ROLE_ADMIN");
        roles.add(adminRole);
        Role userRole = new Role("ROLE_USER");
        roles.add(userRole);
        roleDao.saveRole(adminRole);
        roleDao.saveRole(userRole);


        User admin = new User("admin", "admin", "admin", new HashSet<>());
        admin.getRoles().add(roleDao.getRoleByName("ROLE_ADMIN"));
        userDao.save(admin);

        User user = new User("user", "user", "user", new HashSet<>());
        user.getRoles().add(roleDao.getRoleByName("ROLE_USER"));
        userDao.save(user);

        User user1 = new User("user1", "user1", "user1", roles);
        userDao.save(user1);

    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public List<User> read() {
        return userDao.read();
    }

    @Override
    @Transactional
    public User getUserByName(String name) { return userDao.getUserByName(name);
    }

    @Override
    @Transactional
    public void delete(long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public User update(long id, String name, String lastname, String password, Collection<Role> role) {
        return userDao.update(id, name, lastname, password, role);
    }

    @Override
    public User upPage(long id) {
        return userDao.upPage(id);
    }

}