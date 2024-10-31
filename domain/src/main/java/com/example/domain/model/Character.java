package com.example.domain.model;

public class Character {
    private int id;
    private String name;
    private String species;
    private String status;
    private String image;
    private String gender; // Добавляем поле gender

    public Character(int id, String name, String species, String status, String image, String gender) { // Обновляем конструктор
        this.id = id;
        this.name = name;
        this.species = species;
        this.status = status;
        this.image = image;
        this.gender = gender;
    }

    // Геттеры и сеттеры
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() { // Добавляем геттер для gender
        return gender;
    }

    public void setGender(String gender) { // Добавляем сеттер для gender
        this.gender = gender;
    }
}
