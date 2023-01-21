package ru.home.controllers;

import ru.home.model.User;

import java.util.List;
import java.util.Set;

public interface UserController {
    Long createUser(String name, Set<String> citiesLived, Set<String> citiesWork);
    User getUser(Long id);
    User updateUser(Long id, String name, Set<String> citiesLived, Set<String> citiesWork);
    void deleteUser(Long id);
    List<User> getAllUsers();
    Set<String> getAllCities();
    Set<String> getAllCitiesWhereUserLived(String name);
    Set<String> getAllCitiesWhereUserWorked(String name);
}
