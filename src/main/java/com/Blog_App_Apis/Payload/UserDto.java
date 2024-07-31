package com.Blog_App_Apis.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data    // getter,setter,
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;

    private String name;

    private String email;

    private String password;

    private String about;
}
