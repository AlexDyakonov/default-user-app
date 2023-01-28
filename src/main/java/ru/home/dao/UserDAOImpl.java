package ru.home.dao;

import ru.home.exeption.ApplicationException;
import ru.home.model.User;
import ru.home.sql.SQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            return getAllCities().contains(cityName);
        } catch (RuntimeException e) {
            throw new ApplicationException("Метод checkCity не сработал", e.getCause());
        }
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
                String query = "INSERT INTO city (name) VALUES ('" + cityName + "');";
                PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows == 0){
                    throw new ApplicationException("Создать город не удалось");
                }
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                    if (resultSet.next()){
                        id = resultSet.getLong("id");
                    } else {
                        throw new ApplicationException("id не был возвращен");
                    }
                }
                preparedStatement.close();
                return getCity(cityName);
            } catch (SQLException e){
                throw new ApplicationException("Метод createCity не сработал", e.getCause());
            }
        } else return getCity(cityName);
    }

    public void putCityIdUserIdInTable(Long userId, Long cityId, String table){
        try {
            String query = "INSERT INTO " + table + " (userid, cityid) VALUES ("+ userId +", " + cityId + ")";
            PreparedStatement preparedStatement =  sqlConnection.getConnection().prepareStatement(query);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0){
                throw new ApplicationException("Не получилось добавить город к юзеру");
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new ApplicationException("Метод putCityId... не сработал");
        }
    }

    public void addCityToUser(Long userId, Set<String> citySet, String table){
        Long cityId = 0L;
        for (String cityName : citySet) {
            if (checkCity(cityName)){
                cityId = getCity(cityName);
            } else {
                cityId = createCity(cityName);
            }
            if (cityId == 0L){
                throw new ApplicationException("Не удалось получить cityId");
            }
            putCityIdUserIdInTable(userId, cityId, table);
        }
    }


    public Long addUser(String userName){
        Long id = 0L;
        try {
            String query = "INSERT INTO users (name) VALUES ('" + userName + "')";
            PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0){
                throw new ApplicationException("Не удалось добавить юзера");
            }
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()){
                    id = resultSet.getLong("id");
                } else throw new ApplicationException("Не удалось получить id юзера");
            }
        } catch (SQLException e){
            throw new ApplicationException("Метод addUser не сработал");
        }
        return id;
    }

    @Override
    public Long createUser(User user) {
        Long id = addUser(user.getName());
        if (id == 0L){
            throw new ApplicationException("Метод addUser не сработал");
        }
        addCityToUser(id, user.getCitiesLived(), "lived");
        addCityToUser(id, user.getCitiesWork(), "worked");
        return id;
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
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0){
                throw new ApplicationException("Не удалось удалить юзера");
            }
            preparedStatement.close();
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