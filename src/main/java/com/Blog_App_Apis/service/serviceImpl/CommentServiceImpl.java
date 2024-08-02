package com.Blog_App_Apis.service.serviceImpl;

import com.Blog_App_Apis.Payload.CommentDto;
import com.Blog_App_Apis.entity.Comments;
import com.Blog_App_Apis.entity.Post;
import com.Blog_App_Apis.exception.ResourceNotFoundException;
import com.Blog_App_Apis.repository.CommentRepository;
import com.Blog_App_Apis.repository.PostRepository;
import com.Blog_App_Apis.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postrepo;
    private CommentRepository commentrepo;

    //here we did constructor base injection (it helps to create a bean )
    public CommentServiceImpl(PostRepository postrepo, CommentRepository
            commentrepo) {
        this.postrepo = postrepo;
        this.commentrepo = commentrepo;
    }
    @Override
    public CommentDto createComment(Integer postId, CommentDto commentDto) {
        //first we will check here post is available or not
        //It will search the post
        Post post = postrepo.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("post","postid",postId));
        //post the comment on the postid
        //Object is created for Comment
        Comments comment = new Comments();
        comment.setName(commentDto.getName());//commentdto to setting the comment
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post); // Set the comment for this post
        //Now save the Comment and store into local variable
        Comments savecomment = commentrepo.save(comment);

        //After saving convert back to Dto because we want response in     Postman.
        CommentDto dto = new CommentDto();
        dto.setId(savecomment.getId());
        dto.setName(savecomment.getName());
        dto.setEmail(savecomment.getEmail());
        dto.setBody(savecomment.getBody());
        return dto; //finally Service layer is ready for create a comment
        //it will return to CommentCOntroller
    }

    @Override
    public List<CommentDto> findCommentsByPostId(Integer postId) {
        //first we will check here post is available or not
        //It will search the post
        Post post = postrepo.findById(postId).orElseThrow(() -> new
                ResourceNotFoundException("Post","postid",postId));
        //create the comment on the postid

        //when the post is present then we get the comment
        List<Comments> commentsList = commentrepo.findByPostId(postId);
        // convert list of comment entities to list of comment dto's
        List<CommentDto> commentDtoList = commentsList.stream().map(comment ->
                mapToDto(comment)).collect(Collectors.toList());//entity to dto
        return commentDtoList;
    }

    private static CommentDto mapToDto(Comments comment){
        //CommentDto commentDto = mapper.map(comment, CommentDto.class);
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    static Comments mapToEntity(CommentDto commentDto){
        //Comment comment = mapper.map(commentDto, Comment.class);
        Comments comment = new Comments();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

    @Override
    public CommentDto updateComment(Integer postId, Long id, CommentDto commentDto)
    {
        // retrieve post entity by id
        Post post = postrepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post","postid",id));

        // retrieve comment by id
        Comments comment = commentrepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment","commentid",id));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comments updatedComment = commentrepo.save(comment);//save the save the updated comment in db
        return mapToDto(updatedComment);//convert updated comment into dto.
    }

}
