package org.example.seminar.task1.moduls;

import jakarta.persistence.*;

import java.util.Random;
@Entity
@Table(name = "students")

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;

    private static final String[] names = {"Ярослав", "Максим", "Всеволод", "Мария", "Назар", "Оскар", "Григорий"};
    private static final Random r = new Random();

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student(){}

    public static Student create(){
        return new Student(names[r.nextInt(names.length)], r.nextInt(18, 28));
    }

    public void updateName(){
        name = names[r.nextInt(names.length)];
    }
    public void updateAge(){
        age = r.nextInt(18, 28);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
