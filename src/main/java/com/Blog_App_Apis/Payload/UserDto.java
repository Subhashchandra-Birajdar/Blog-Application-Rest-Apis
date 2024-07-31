package com.Blog_App_Apis.Payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data    // getter,setter,
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    //@NotEmpty - it provide @NotNUll + @NotBlank
    @NotEmpty
    @Size(min = 4, message = "username must be min of 4 character !!!")
    private String name;

    @Email(message = "email address is not valid !!!")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 8, message = "Password must be min of 4 chars and max of 8 char !!! ")
    private String password;

    @NotEmpty
    private String about;
}