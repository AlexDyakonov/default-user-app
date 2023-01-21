package ru.home.ui;

import ru.home.controllers.UserController;
import ru.home.controllers.UserControllerImpl;
import ru.home.dao.UserDAOImpl;
import ru.home.services.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleUserInterface {
    public static void menu(){
        System.out.println("Добро пожаловать! Что желаете сделать?\n" +
                "   1. Добавить пользователя\n" +
                "   2. Получить данные о пользователе\n" +
                "   3. Изменить данные пользователя\n" +
                "   4. Удалить пользователя\n" +
                "   5. Получить всех пользователей\n" +
                "   6. Получить все города\n" +
                "   7. Получить все города, в которых проживали пользователи\n" +
                "   8. Получить все города, в которыж работали пользователи\n" +
                "   0. Вывести меню повторно\n" +
                "   e. Закончить работу\n" +
                "Выберите:");
    }
    public static void start() {
        menu();

        UserControllerImpl userController = new UserControllerImpl(new UserServiceImpl(new UserDAOImpl()));

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String command = "";
            while (!(command = reader.readLine()).equals("exit")){
                switch (command) {
                    case "0":
                        menu();
                        break;
                    case "1":
                        System.out.println("Для добавления пользователя введите:");
//                    userController.createUser(, );
                        break;
                    case "2":
                        System.out.println("Введите id пользователя: ");
                        userController.getUser(Long.parseLong(reader.readLine()));
                        break;
                    case "3":
                        System.out.println("Чтобы изменить данные пользователя введите, что хотите изменить");
//                    userController.updateUser();
                        break;
                    case "4":
                        System.out.println("Введите id пользователя, которого хотите удалить");
                        userController.getUser(Long.parseLong(reader.readLine()));
                        break;
                    case "5":
                        System.out.println("Список всех пользователей:");
                        userController.getAllUsers();
                        break;
                    case "6":
                        System.out.println("6");
                        break;
                    case "7":
                        System.out.println("7");
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