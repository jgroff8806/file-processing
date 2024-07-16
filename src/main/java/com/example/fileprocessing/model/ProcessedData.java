package com.example.fileprocessing.model;

public class ProcessedData {
    private String name;
    private int age;

    // No-argument constructor
    public ProcessedData() {}

    // Constructor with parameters
    public ProcessedData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter and Setter methods
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
}
