package com.Blog_App_Apis.service.serviceImpl;

import com.Blog_App_Apis.Payload.PostDto;
import com.Blog_App_Apis.entity.Category;
import com.Blog_App_Apis.entity.Post;
import com.Blog_App_Apis.entity.User;
import com.Blog_App_Apis.exception.ResourceNotFoundException;
import com.Blog_App_Apis.repository.CategoryRepository;
import com.Blog_App_Apis.repository.PostRepository;
import com.Blog_App_Apis.repository.UserRepository;
import com.Blog_App_Apis.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer CategoryId) {
        // find the user, if user found then only he will post
        User user = this.userrepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        // find the category, if category found then only user can add post to the perticular category
        Category category = this.categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", CategoryId));

        // add post and convert into the PostDto to Post entity
        Post post  = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());  //4.5
        post.setUser(user);
        post.setCategory(category);

        // save the post
        Post savepost = this.postRepository.save(post);

        // convert Postentity to PostDto
        return this.modelMapper.map(savepost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatePost = this.postRepository.save(post);
        PostDto updatepostDto = this.modelMapper.map(updatePost,PostDto.class);
        return updatepostDto;
    }

    @Override
    public void deletePost(Integer postid) {
        Post post = this.postRepository.findById(postid).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postid));
        this.postRepository.delete(post);
    }

    @Override
    public List<PostDto> getAllPost() {
        //Pageable p = PageRequest.of(pageNumber, pageSize); // here get the pageable class here we get pagesize,pagenumber
        List<Post> pagePost = this.postRepository.findAll(); // pageable return the page
        //List<Post> allposts = pagePost.getContent();  // page through we get the content and posts
        List<PostDto> postDtos = pagePost.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos; // return the postDtos at client
    }

    @Override
    public PostDto getpostById(Integer postId) {
        Post posts = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        PostDto postDto = modelMapper.map(posts, PostDto.class);
        return postDto;
    }
}
