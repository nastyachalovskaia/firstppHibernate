package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String SQL_CREATE = "CREATE TABLE USERS"
                + "("
                + " ID bigint auto_increment PRIMARY KEY,"
                + " NAME varchar(100) NOT NULL,"
                + " LASTNAME varchar(100) NOT NULL,"
                + " AGE tinyint"
                + ")";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(SQL_CREATE);

            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Не удалось создать таблицу.");
        }
    }

    public void dropUsersTable() {
        String SQL_DROP = "DROP TABLE USERS";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(SQL_DROP);

            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Не удалось удалить таблицу.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String SQL_SAVE_USER = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";


        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Не удалось сохранить пользователя.");
        }
    }

    public void removeUserById(long id) {
        String SQL_REMOVE_USERS_BY_ID = "DELETE FROM USERS WHERE ID = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_USERS_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Не удалось удалить пользователя по айди.");
        }
    }

    public List<User> getAllUsers() {

        String SQL_GET_ALL_USERS = "select * from users";

        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            resultSet = statement.executeQuery(SQL_GET_ALL_USERS);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
            }

            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Не удалось вернуть таблицу.");
        }
        return userList;
    }

    public void cleanUsersTable() {
        String SQL_CLEAN_USERS_TABLE = "TRUNCATE TABLE USERS";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(SQL_CLEAN_USERS_TABLE);
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Не удалось очистить таблицу.");

        }

    }
}
