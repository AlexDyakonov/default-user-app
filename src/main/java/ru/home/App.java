package ru.home;

// почитать про Нормализацию бд (Нормализация отношений, шесть нормальныйх форм)

import ru.home.controllers.UserController;
import ru.home.controllers.UserControllerImpl;
import ru.home.dao.UserDAOImpl;
import ru.home.services.UserServiceImpl;
import ru.home.ui.ConsoleUserInterface;
import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        UserController userController = new UserControllerImpl(new UserServiceImpl(new UserDAOImpl()));
        ConsoleUserInterface.start();
    }
}
