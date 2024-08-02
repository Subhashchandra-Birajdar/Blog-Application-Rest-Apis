package com.Blog_App_Apis.controller;

import com.Blog_App_Apis.Payload.CommentDto;
import com.Blog_App_Apis.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable(value = "postId") Integer postId,
            @RequestBody CommentDto commentDto){
        CommentDto cmtdto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(cmtdto, HttpStatus.CREATED);
//return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    //http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId")
                                                Integer postId){
        List<CommentDto> cmtdtos = commentService.findCommentsByPostId(postId);
        return cmtdtos;
    }

    //update comment by commentid the comment (Means A single comment not all)
//http://localhost:8080/api/posts/1/comments/1
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById(
            @PathVariable(value = "postId") Integer postId,
            @PathVariable(value = "id") Long Id,
            @RequestBody CommentDto commentDto) {
        //here we use commentdto because updated   comment we will save into entity
        CommentDto updatedComment = commentService.updateComment(postId, Id, commentDto);
        return new ResponseEntity<>(updatedComment,HttpStatus.CREATED);
    }
}
