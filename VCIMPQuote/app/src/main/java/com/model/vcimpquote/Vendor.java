package com.model.vcimpquote;

public class Vendor {
    private int id;
    private String name;
    private String email;
    private String position;
    private Double salary;

    public Vendor() {
    }

    public Vendor(int id, String name, String email, String position, Double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
        this.salary = salary;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDataQuery() {
        return id + ", \"" + name + "\", \"" + email + "\", \"" + position + "\", " + salary;
    }
}
