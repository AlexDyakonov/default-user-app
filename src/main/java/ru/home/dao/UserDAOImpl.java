package ru.home.dao;

import ru.home.exeption.ApplicationException;
import ru.home.model.User;
import ru.home.sql.SQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDAOImpl implements UserDAO{
    private final SQLConnection sqlConnection;

    public UserDAOImpl() {
        this.sqlConnection = new SQLConnection();
    }

    public boolean checkCity(String cityName){
        boolean answer = false;
        try {
            for(String item : getAllCities()){
                if ((cityName.equals(item))) {
                    answer = true;
                    break;
                }
            }
        } catch (RuntimeException e) {
            throw new ApplicationException("Метод checkCity не сработал", e.getCause());
        }
        return answer;
    }

    public Long getCity(String cityName){
        Long cityId = null;
        try {
            String query = "SELECT city.id FROM city WHERE name = '" + cityName + "'";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                cityId = resultSet.getLong("id");
            }
            preparedStatement.close();
        } catch (SQLException e){
            throw new ApplicationException("Метод getCity не сработал", e.getCause());
        }
        return cityId;
    }

    public Long createCity(String cityName){
        Long id = null;
        if (!checkCity(cityName)){
            try {
                String query = "INSERT INTO city.name " +
                        "VALUES '" + cityName + "'";
                PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
                preparedStatement.close();
                return getCity(cityName);
            } catch (SQLException e){
                throw new ApplicationException("Метод createCity не сработал", e.getCause());
            }
        } else return getCity(cityName);
    }
    public void addCityWorkedToUser(Long userId, Long cityId){
        
    }
    public void addCityLivedToUser(Long userId, Long cityId){

    }

    public void addCityWorkedLived(Long userId, Long cityId, char choice){
        switch (choice){
            case 'w' -> addCityWorkedToUser(userId, cityId);
            case 'l' -> addCityLivedToUser(userId, cityId);
        }
    }

    @Override
    public Long createUser(User user) {
//      getCityId(стринг из сета и возвращает id или 0 если нет), createCity, addCityIdWorked/Lived (user id, city id)
        return null;
    }

    @Override
    public User getUser(Long id) {
        User user = null;
        try {
            String query = "SELECT users.name FROM users " +
                    "WHERE users.id = '" + id + "'";
            String name = null;
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                name = resultSet.getString("name");
            }
            Set<String> worked = getAllCitiesWhereUserWorked(name);
            Set<String> lived = getAllCitiesWhereUserLived(name);
            preparedStatement.close();
            user = new User(id, name, lived, worked);
        } catch (SQLException e) {
            throw new ApplicationException("Метод getUser не сработал", e.getCause());
        }
        return user;
    }

    @Override
    public User updateUser(Long id, User user) {
//        меняются только города. Просто добавляются к юзеру новые города.
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        try {
            String query = "DELETE FROM users WHERE id = '" + id + "'";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            preparedStatement.close();
            System.out.println("Пользователь с id" + id + " удален.");
        } catch (SQLException e){
            throw new ApplicationException("Метод deleteUser не сработал", e.getCause());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        try {
            String query = "SELECT users.id " +
                    "FROM users ";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user.add(getUser(resultSet.getLong("id")));
            }
            preparedStatement.close();
        } catch (SQLException e){
            throw new ApplicationException("Метод getAllUsers не сработал", e.getCause());
        }
        return user;
    }

    @Override
    public Set<String> getAllCities() {
        Set<String> city = new HashSet<>();
        try {
            String query = "SELECT city.name " +
                    "FROM city";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                city.add(resultSet.getString("name"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new ApplicationException("Метод getAllCities не сработал", e.getCause());
        }
        return city;
    }

    @Override
    public Set<String> getAllCitiesWhereUserLived(String name) {
        Set<String> city = new HashSet<>();
        try {
            String query = "SELECT city.name " +
                    "FROM users, city, lived " +
                    "WHERE users.id = lived.userid " +
                    "AND city.id = lived.cityid " +
                    "AND users.name = '" + name + "'";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                city.add(resultSet.getString("name"));
            }
            preparedStatement.close();
        } catch (SQLException e){
            throw new ApplicationException("Метод getAllCitiesWhereUserLived не сработал", e.getCause());
        }
        return city;
    }

    @Override
    public Set<String> getAllCitiesWhereUserWorked(String name) {
        Set<String> city = new HashSet<>();
        try {
            String query = "SELECT city.name " +
                    "FROM users, city, worked " +
                    "WHERE users.id = worked.userid " +
                    "AND city.id = worked.cityid " +
                    "AND users.name = '" + name + "'";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                city.add(resultSet.getString("name"));
            }
            preparedStatement.close();
        } catch (SQLException e){
            throw new ApplicationException("Метод getAllCitiesWhereUserWorked не сработал", e.getCause());
        }
        return city;
    }
}