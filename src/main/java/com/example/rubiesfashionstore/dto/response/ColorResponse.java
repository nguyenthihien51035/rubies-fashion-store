package com.example.rubiesfashionstore.dto.response;

public class ColorResponse {
    private Integer id;
    private String name;

    public ColorResponse() {
    }

    public ColorResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
