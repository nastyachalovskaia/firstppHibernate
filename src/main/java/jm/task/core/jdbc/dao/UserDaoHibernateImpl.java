package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL_CREATE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Не удалось создать таблицу.");
        }
    }

    @Override
    public void dropUsersTable() {
        String SQL_DROP = "drop table if exists users";

        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL_DROP).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Не удалось удалить таблицу.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Не удалось сохранить пользователя.");
        }

    }

    @Override
    public void removeUserById(long id) {
        String SQL_REMOVE_USERS_BY_ID = "delete User where id =: currentId";
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(SQL_REMOVE_USERS_BY_ID);
            query.setParameter("currentId", id);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Не удалось удалить пользователя по айди.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        String SQL_GET_ALL_USERS = "select * from users";
        List<User> userList = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            userList = session.createSQLQuery(SQL_GET_ALL_USERS)
                    .addEntity(User.class)
                    .list();
        } catch (Exception e) {
            System.err.println("Не удалось получить пользователя.");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String SQL_CLEAN_USERS_TABLE = "TRUNCATE TABLE USERS";
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(SQL_CLEAN_USERS_TABLE).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Не удалось очистить таблицу.");
        }
    }
}
