package com.Blog_App_Apis.service;

import com.Blog_App_Apis.Payload.CommentDto;
import com.Blog_App_Apis.entity.Comments;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Integer postId, CommentDto commentDto);
//postid (if post is exist then for that , we can comment on that post)

    List<CommentDto> findCommentsByPostId(Integer postId);

    CommentDto updateComment(Integer postId, Long id, CommentDto commentDto);

}
