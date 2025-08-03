package com.example.rubiesfashionstore.form.user;

import com.example.rubiesfashionstore.model.Role;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserByAdminForm {

    private String firstName;
    private String lastName;

    @Pattern(regexp = "^(0[0-9]{9})$", message = "Số điện thoại không hợp lệ")
    private String phone;

    private Role role;
}
