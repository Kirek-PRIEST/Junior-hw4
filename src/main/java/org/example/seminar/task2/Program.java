package org.example.seminar.task2;

import org.example.seminar.task1.moduls.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Program {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory()) {
            //Начало сессии
            Session session = sessionFactory.getCurrentSession();

            //Начало транзакции
            session.beginTransaction();

            //Создание объекта
            Student student = Student.create();
            session.save(student);
            System.out.println("Объект Студент создан удачно");

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
