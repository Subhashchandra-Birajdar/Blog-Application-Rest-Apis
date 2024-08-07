package com.Blog_App_Apis.repository;

import com.Blog_App_Apis.entity.Category;
import com.Blog_App_Apis.entity.Post;
import com.Blog_App_Apis.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    @Query("select p from Post p where p.title like:key")
    List<Post> searchByTitle(@Param("key")String title);
    //or
    //List<Post> searchByTitleContaining(String title);
}

