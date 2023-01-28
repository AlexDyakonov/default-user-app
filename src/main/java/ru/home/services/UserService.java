package ru.home.services;

import ru.home.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void addCityToUser(Long userId, Set<String> citySet, String table);
    Long createUser(User user);
    User getUser(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    List<User> getAllUsers();
    Set<String> getAllCities();
    Set<String> getAllCitiesWhereUserLived(String name);
    Set<String> getAllCitiesWhereUserWorked(String name);
}
