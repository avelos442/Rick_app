package com.example.data.model;

import java.util.List;

public class CharacterResponse {
    private List<Character> results;

    public List<Character> getResults() {
        return results;
    }

    public static class Character {
        private int id; // ID персонажа
        private String name;
        private String species;
        private String status;
        private String image;
        private String gender; // Добавлено поле gender

        // Геттеры
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSpecies() {
            return species;
        }

        public String getStatus() {
            return status;
        }

        public String getImage() {
            return image;
        }

        public String getGender() { // Добавлен геттер для gender
            return gender;
        }
    }
}
