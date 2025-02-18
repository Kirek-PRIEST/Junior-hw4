package org.example.hw;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) {


        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Max", 34));
        personList.add(new Person("Ivan", 55));
        personList.add(new Person("Petr", 14));
        personList.add(new Person("Ilya", 45));
        personList.add(new Person("Georgy", 78));

        createTable();

        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate2.cfg.xml")
                .addAnnotatedClass(Person.class)
                .buildSessionFactory()) {
            //Начало сессии
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            for (Person person : personList) {
                session.persist(person);
            }
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Создание базы данных и таблицы persons через jdbc.
    private static void createTable() {

        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "admin";
        String query1 = "CREATE DATABASE IF NOT EXISTS personsDB;";
        String query2 = "USE personsDB;";
        String query3 = "CREATE TABLE IF NOT EXISTS persons (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(45), age INT);";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.execute();
            PreparedStatement statement1 = connection.prepareStatement(query2);
            statement1.execute();
            PreparedStatement statement2 = connection.prepareStatement(query3);
            statement2.execute();

            System.out.println("База данных и таблица созданы.");
            System.out.println();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
