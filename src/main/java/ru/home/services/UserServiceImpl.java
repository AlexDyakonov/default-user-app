package ru.home.services;

import ru.home.dao.UserDAO;
import ru.home.model.User;
import java.util.List;
import java.util.Set;

// методы сервиса вызывают методы DAO

public class UserServiceImpl implements UserService{
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Long createUser(User user) {
        return userDAO.createUser(user);
    }

    @Override
    public User getUser(Long id) {
        return userDAO.getUser(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        return userDAO.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public Set<String> getAllCities() {
        return userDAO.getAllCities();
    }

    @Override
    public Set<String> getAllCitiesWhereUserLived(String name) {
        return userDAO.getAllCitiesWhereUserLived(name);
    }

    @Override
    public Set<String> getAllCitiesWhereUserWorked(String name) {
        return userDAO.getAllCitiesWhereUserWorked(name);
    }
}
