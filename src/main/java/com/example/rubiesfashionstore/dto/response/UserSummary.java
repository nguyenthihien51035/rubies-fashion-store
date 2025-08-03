package com.example.rubiesfashionstore.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummary {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
