package ru.home.controllers;

import ru.home.model.User;
import ru.home.services.UserService;

//во всех методах контроллера сначала происходит валидация данных, а после вызов метода сервиса

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UserControllerImpl implements UserController{
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = Objects.requireNonNull(userService, "User service must be provided.");
    }

    @Override
    public Long createUser(String name, Set<String> citiesLived, Set<String> citiesWork) {
//        Валидация имени, сета городов, где жил/работал
        User user = new User(name, citiesLived, citiesWork);
        return userService.createUser(user);
    }

    @Override
    public User getUser(Long id) {
        return userService.getUser(id);
    }

    @Override
    public User updateUser(Long id, String name, Set<String> citiesLived, Set<String> citiesWork) {
        User user = new User(id, name, citiesLived, citiesWork);
        return userService.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public Set<String> getAllCities() {
        return userService.getAllCities();
    }

    @Override
    public Set<String> getAllCitiesWhereUserLived(String name) {
        return userService.getAllCitiesWhereUserLived(name);
    }

    @Override
    public Set<String> getAllCitiesWhereUserWorked(String name) {
        return userService.getAllCitiesWhereUserLived(name);
    }
}
