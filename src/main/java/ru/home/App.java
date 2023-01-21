package ru.home;

// почитать про Нормализацию бд (Нормализация отношений, шесть нормальныйх форм)

import ru.home.controllers.UserController;
import ru.home.controllers.UserControllerImpl;
import ru.home.dao.UserDAO;
import ru.home.dao.UserDAOImpl;
import ru.home.services.UserServiceImpl;

import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        UserController userController = new UserControllerImpl(new UserServiceImpl(new UserDAOImpl()));
        UserDAO userDAO = new UserDAOImpl();
        System.out.println(userDAO.createCity("Приозерск"));
        System.out.println(userDAO.createCity("Бебра"));
//        ConsoleUserInterface.start();
    }
}
