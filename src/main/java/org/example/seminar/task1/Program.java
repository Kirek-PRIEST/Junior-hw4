package org.example.seminar.task1;

import org.example.seminar.task1.moduls.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Program {
    private static final Random r = new Random();

    public static void main(String[] args) {


        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "admin";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            createDatabase(connection);
            System.out.println("Команда CREATE DATABASE выполнена успешно");

            useDatabase(connection);
            System.out.println("Команда USE выполнена успешно");

            createTable(connection);
            System.out.println("Команда CREATE TABLE выполнена успешно");

            int count = r.nextInt(5, 11);
            for (int i = 0; i < count; i++) {
                insertData(connection, Student.create());
                ;
            }
            System.out.println("Команда INSERT успешно выполнена");
            System.out.println();

            Collection<Student> students = readData(connection);
            for (var student : students) {
                System.out.println(student);
            }
            System.out.println("Данные из таблицы прочитаны успешно");
            System.out.println();

            for (var student : students) {
                student.updateName();
                student.updateAge();
                updateData(connection, student);
            }
            System.out.println("Команда UPDATE выполнена успешно");

            students = readData(connection);
            for (var student : students) {
                System.out.println(student);
            }
            System.out.println("Данные из таблицы прочитаны успешно");
            System.out.println();

            for (var student : students) {
                deleteData(connection, student.getId());
            }
            System.out.println("Команда DELETE выполнена успешно");

            connection.close();
            System.out.println("Соединение с БД закрыто");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase(Connection connection) throws SQLException {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS studentsDB";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void useDatabase(Connection connection) throws SQLException {
        String useDatabaseSQL = "USE studentsDB";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            ;
            statement.execute();

        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(45), age INT)";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    private static void insertData(Connection connection, Student student) throws SQLException {
        String insertDataSQL = "INSERT INTO students (name, age) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.execute();

        }
    }

    private static Collection<Student> readData(Connection connection) throws SQLException {
        ArrayList<Student> studentList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM students;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while ((resultSet.next())) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                studentList.add(new Student(id, name, age));
            }

        }
        return studentList;
    }

    private static void updateData(Connection connection, Student student) throws SQLException {
        String updateDataSQL = "UPDATE students SET name=?, age=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateDataSQL)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setInt(3, student.getId());
            statement.executeUpdate();
        }
    }

    private static void deleteData(Connection connection, int id) throws SQLException{
        String deleteDataSQL = "DELETE FROM students WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteDataSQL)){
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

}
