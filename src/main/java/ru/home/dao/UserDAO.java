package ru.home.dao;

import ru.home.model.User;

import java.util.List;
import java.util.Set;

public interface UserDAO {
    void addCityToUser(Long userId, Set<String> citySet, String table);
    boolean checkCity(String cityName);
    Long createUser(User user);
    User getUser(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    List<User> getAllUsers();
    Set<String> getAllCities();
    Set<String> getAllCitiesWhereUserLived(String name);
    Set<String> getAllCitiesWhereUserWorked(String name);
}
