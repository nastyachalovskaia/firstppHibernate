package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Bob", "Bobovitch", (byte) 33);
        userService.saveUser("Tayler", "Derden", (byte) 37);
        userService.saveUser("Marla", "Singer", (byte) 31);

        userService.removeUserById(2);

        List<User> users = userService.getAllUsers();
        System.out.println(users);

        userService.cleanUsersTable();

        userService.dropUsersTable();


    }
}
