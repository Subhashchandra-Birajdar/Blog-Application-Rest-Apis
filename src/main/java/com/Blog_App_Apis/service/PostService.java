package com.Blog_App_Apis.service;

import com.Blog_App_Apis.Payload.PostDto;
import com.Blog_App_Apis.Payload.PostResponse;
import com.Blog_App_Apis.entity.Post;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId );
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postid);
    //get all post of users
    List<PostDto> getAllPost();
    //get single post
    PostDto getpostById(Integer postId);

    // get all post by category
    List<PostDto> getPostByCategory(Integer categoryId);

    // get all post by user
    List<PostDto> getPostByUser(Integer userId);

    // search posts
    List<PostDto> searchPosts(String keyword);

    public List<PostDto> getAllPosts(int pageNumber, int pageSize);

    public PostResponse getAllPostsWithPageResponse(Integer pageNumber, Integer pageSize);

    PostResponse getAllPostWithSortingByAndDir(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostResponse getAllPostswithSortingAndOrderwithAppConstant(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


}

