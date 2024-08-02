package com.Blog_App_Apis.Payload;

import lombok.Data;
@Data // don't require encapsulation in traditional way so , we get getter and setter from this annotation.
public class CommentDto {
    private long id;
    private String name;
    private String email;
    private String body;
}
