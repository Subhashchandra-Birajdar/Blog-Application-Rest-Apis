package com.Blog_App_Apis.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse{
    private List<PostDto> content;
    private int pageNumber;
    private int pageSize;
    private Integer totalElements;
    private int totalPages;
    private boolean lastPage;


    //private int content;

// getter, setter, allargscontructor, noargsconstructor
}

