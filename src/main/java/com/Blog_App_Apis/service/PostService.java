package com.Blog_App_Apis.service;

import com.Blog_App_Apis.Payload.PostDto;
import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId );
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postid);
    //get all post of users
    List<PostDto> getAllPost();
    //get single post
    PostDto getpostById(Integer postId);
}

