package com.Blog_App_Apis.repository;

import com.Blog_App_Apis.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {  }

