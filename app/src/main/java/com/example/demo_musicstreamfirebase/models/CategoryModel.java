package com.example.demo_musicstreamfirebase.models;


import java.util.List;
public class CategoryModel {

    private String name;
    private String coverUrl;
    private List<String> songs;

    // Constructor đầy đủ
    public CategoryModel(String name, String coverUrl, List<String> songs) {
        this.name = name;
        this.coverUrl = coverUrl;
        this.songs = songs;
    }

    // Constructor mặc định (không đối số)
    public CategoryModel() {
        this("", "", List.of());
    }

    // Getter và Setter cho các thuộc tính
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }
}
