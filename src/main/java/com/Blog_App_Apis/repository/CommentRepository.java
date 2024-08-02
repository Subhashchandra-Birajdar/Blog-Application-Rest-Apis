package com.Blog_App_Apis.repository;

import com.Blog_App_Apis.Payload.CommentDto;
import com.Blog_App_Apis.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    List<Comments> findByPostId(long postId);
    //List<CommentDto> findbyPostIdAndEmail(long Id,String email);

}
