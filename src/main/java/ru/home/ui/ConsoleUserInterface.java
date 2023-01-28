package ru.home.ui;

import ru.home.controllers.UserControllerImpl;
import ru.home.dao.UserDAOImpl;
import ru.home.model.User;
import ru.home.services.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ConsoleUserInterface {
    private static final UserControllerImpl userController = new UserControllerImpl(new UserServiceImpl(new UserDAOImpl()));


    public static void menu(){
        System.out.println("Добро пожаловать! Что желаете сделать?\n" +
                "   1. Добавить пользователя\n" +
                "   2. Получить данные о пользователе\n" +
                "   3. Изменить данные пользователя\n" +
                "   4. Удалить пользователя\n" +
                "   5. Получить всех пользователей\n" +
                "   6. Получить все города\n" +
                "   7. Получить все города, в которых проживал пользователь (по имени)\n" +
                "   8. Получить все города, в которыж работал пользователь (по имени)\n" +
                "   0. Вывести меню повторно\n" +
                "   e. Закончить работу\n" +
                "Выберите:");
    }

    public static Set<String> addCityToSet(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            Set<String> citySet = new HashSet<>();
            String city;
            while (!((city = reader.readLine()).equals("e"))){
                citySet.add(city);
            }
            return citySet;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() {
        menu();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            String command = "";
            User user;
            String name;
            String city;
            Long id;
            while (!(command = reader.readLine()).equals("exit")){
                switch (command) {
                    case "0":
                        menu();
                        break;
                    case "1":
                        System.out.println("Для добавления пользователя введите имя");
                        name = reader.readLine();
                        System.out.println("Города в которых пользователь жил (чтобы остановить ввод, напишите 'e' )");
                        Set<String> citiesLived = addCityToSet();
                        System.out.println("Города в которых пользователь работал (чтобы остановить ввод, напишите 'e' )");
                        Set<String> citiesWorked = addCityToSet();
                        id = userController.createUser(name, citiesLived, citiesWorked);
                        System.out.println("Пользователь " + name + " создан, его id - " + id);
                        break;
                    case "2":
                        System.out.println("Введите id пользователя: ");
                        id = Long.parseLong(reader.readLine());
                        user = userController.getUser(id);
                        System.out.println(user);
                        break;
                    case "3":
                        System.out.println("Введите id пользователя, данные которого вы хотите изменить:");
                        id = Long.parseLong(reader.readLine());
                        System.out.println("Введите 1, если хотите изменить города, где пользователь жил, 2 -- где работал");
                        String answer = reader.readLine();
                        System.out.println("Введите названия городов: (чтобы остановить ввод, напишите 'e' )");
                        switch (answer) {
                            case "1" -> userController.addCityToUser(id, addCityToSet(), "lived");
                            case "2" -> userController.addCityToUser(id, addCityToSet(), "worked");
                            default -> System.out.println("Вы ввели неверное значение");
                        }
                        System.out.println("Данные пользователя успешно обновлены");
                        break;
                    case "4":
                        System.out.println("Введите id пользователя, которого хотите удалить");
                        id = Long.parseLong(reader.readLine());
                        userController.deleteUser(id);
                        System.out.println("Пользователь с id - " + id + " успешно удален.");
                        break;
                    case "5":
                        System.out.println("Список всех пользователей:");
                        System.out.println(userController.getAllUsers().toString());
                        break;
                    case "6":
                        System.out.println("Список всех городов:");
                        System.out.println(userController.getAllCities().toString());
                        break;
                    case "7":
                        System.out.println("Введите имя пользователя, города которого вы хотите узнать");
                        name = reader.readLine();
                        System.out.println("Список всех городов, где проживал пользователь " + name);
                        System.out.println(userController.getAllCitiesWhereUserLived(name));
                        break;
                    case "8":
                        System.out.println("Введите имя пользователя, города которого вы хотите узнать");
                        name = reader.readLine();
                        System.out.println("Список всех городов, где работал пользователь " + name);
                        System.out.println(userController.getAllCitiesWhereUserWorked(name));
                        break;
                    default:
                        System.out.println("Вы ввели значение не из меню");
                        break;
                }
        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}