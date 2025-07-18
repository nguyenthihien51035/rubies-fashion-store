package com.example.rubiesfashionstore.dto.response;

public class ColorResponse {
    private Integer id;
    private String name;
    private String hexCode;

    public ColorResponse() {
    }

    public ColorResponse(Integer id, String name, String hexCode) {
        this.id = id;
        this.name = name;
        this.hexCode = hexCode;
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

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }
}
