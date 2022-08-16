package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String SQL_CREATE = "CREATE TABLE USERS"
                + "("
                + " ID bigint auto_increment PRIMARY KEY,"
                + " NAME varchar(100) NOT NULL,"
                + " LASTNAME varchar(100) NOT NULL,"
                + " AGE tinyint"
                + ")";
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery(SQL_CREATE).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            System.err.println("Не удалось создать таблицу.");
        }
    }

    @Override
    public void dropUsersTable() {
        String SQL_DROP = "DROP TABLE USERS";
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery(SQL_DROP).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            System.err.println("Не удалось удалить таблицу.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        } catch (
                Exception e) {
            System.err.println("Не удалось сохранить пользователя.");
        }

    }

    @Override
    public void removeUserById(long id) {
        String SQL_REMOVE_USERS_BY_ID = "DELETE FROM users WHERE ID =: currentID";
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                Query query = session.createQuery(SQL_REMOVE_USERS_BY_ID);
                query.setParameter("currentID", id);
                query.executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            System.err.println("Не удалось удалить пользователя по айди.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        String SQL_GET_ALL_USERS = "select * from users";
        List<User> userList = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                userList = session.createSQLQuery(SQL_GET_ALL_USERS)
                        .addEntity(User.class)
                        .list();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            System.err.println("Не удалось получить пользователя.");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String SQL_CLEAN_USERS_TABLE = "TRUNCATE TABLE USERS";
        try (Session session = Util.getSessionFactory().openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery(SQL_CLEAN_USERS_TABLE).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        } catch (Exception e) {
            System.err.println("Не удалось очистить таблицу.");
        }
    }
}
