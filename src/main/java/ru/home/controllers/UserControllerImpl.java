package ru.home.controllers;

import ru.home.exeption.ValidationException;
import ru.home.model.User;
import ru.home.services.UserService;

//во всех методах контроллера сначала происходит валидация данных, а после вызов метода сервиса

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class UserControllerImpl implements UserController{
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = Objects.requireNonNull(userService, "User service must be provided.");
    }

    @Override
    public void addCityToUser(Long userId, Set<String> citySet, String table) {
        validate(userId, this::validateId, "Invalid id not NULL >0");
        validate(citySet, this::validateCitySet, "Invalid city lived length. >1 <64");
        validate(table, this::validateTable, "Invalid table name. Only 'lived' and 'worked' could be.");
        userService.addCityToUser(userId, citySet, table);
    }

    @Override
    public Long createUser(String name, Set<String> citiesLived, Set<String> citiesWork) {
        validate(name, this::validateUserName, "Invalid user name length. User name should be >1 <64");
        validate(citiesLived, this::validateCitySet, "Invalid city lived length. >1 <64");
        validate(citiesWork, this::validateCitySet, "Invalid city worked. >1 <64");
        User user = new User(name, citiesLived, citiesWork);
        return userService.createUser(user);
    }

    @Override
    public User getUser(Long id) {
        validate(id, this::validateId, "Invalid id not NULL >0");
        return userService.getUser(id);
    }

    @Override
    public User updateUser(Long id, String name, Set<String> citiesLived, Set<String> citiesWork) {
        validate(id, this::validateId, "Invalid id not NULL >0");
        validate(name, this::validateUserName, "Invalid user name. Should be >1 <64");
        validate(citiesLived, this::validateCitySet, "Invalid city lived length. >1 <64");
        validate(citiesWork, this::validateCitySet, "Invalid city worked. >1 <64");
        User user = new User(id, name, citiesLived, citiesWork);
        return userService.updateUser(id, user);
    }

    @Override
    public void deleteUser(Long id) {
        validate(id, this::validateId, "Invalid id not NULL >0");
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
        return userService.getAllCitiesWhereUserWorked(name);
    }

    private boolean validateUserName(String userName){
        return (userName != null && userName.length() >= User.MIN_USER_NAME_LENGTH && userName.length() <= User.MAX_USER_NAME_LENGTH);
    }
    private boolean validateCityName(String cityName){
        return (cityName != null && cityName.length() >= User.MIN_CITY_NAME_LENGTH && cityName.length() <= User.MAX_CITY_NAME_LENGTH);
    }
    private boolean validateCitySet(Set<String> citySet){
        return (citySet != null && !citySet.isEmpty() && citySet.stream().allMatch((this::validateCityName)));
    }

    private boolean validateId(Long id){
        return (id != null && id > 0);
    }

    private boolean validateTable(String table){
        return (table.equals("lived") || table.equals("worked"));
    }

    private <T> void validate(T object, Function<T, Boolean> validator, String errorMessage){
        if (!validator.apply(object)){
            throw new ValidationException(errorMessage);
        }
    }
}
