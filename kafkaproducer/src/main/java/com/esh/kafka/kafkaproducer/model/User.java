package com.esh.kafka.kafkaproducer.model;

public class User {

    private String name;
    private String role;
    private String company;
    private Long id;

    public User(String name, String role, String company, Long id) {
        this.name = name;
        this.role = role;
        this.company = company;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
