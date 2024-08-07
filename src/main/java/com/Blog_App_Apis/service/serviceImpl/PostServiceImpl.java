package com.Blog_App_Apis.service.serviceImpl;

import com.Blog_App_Apis.Payload.PostDto;
import com.Blog_App_Apis.Payload.PostResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Post posts = this.postRepository.findById(postId).
                orElseThrow(()-> new ResourceNotFoundException("Post","post id",postId));
        PostDto postDto = modelMapper.map(posts, PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        //finding the category
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        //here finding the category by post
        List<Post> posts = this.postRepository.findByCategory(category); // here we get the category through post
        //post convert to post into post Dtos
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userrepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Post> posts = this.postRepository.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNumber, int pageSize){
        //int pageSize = 5; // this we will get through parameter because we use @RequestParam here
        //int pageNumber = 1; // this also first change the PostService method
        Pageable p = PageRequest.of(pageNumber,pageSize); // here get the pageable class here we get pagesize,pagenumber
        Page<Post> pagePost = this.postRepository.findAll(p); // pageable return the page
        List<Post> allposts = pagePost.getContent();  // page through we get the content and posts
        // convert post to postDtos
//        List<PostDto> postDtos = new ArrayList<>();
//
//        for (Post post : allposts) {
//            PostDto postDto = modelMapper.map(post, PostDto.class);
//            postDtos.add(postDto);
//        }
        List<PostDto> postDtoListWithPagination = allposts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtoListWithPagination; // return the postDtos at client
    }

    @Override
    public PostResponse getAllPostsWithPageResponse(Integer pageNumber, Integer pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize); // here get the pageable class here we get pagesize,pagenumber
        Page<Post> pagePost = this.postRepository.findAll(p); // pageable return the page
        List<Post> allposts = pagePost.getContent();  // page through we get the content and posts
        List<PostDto> postDtos = allposts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        // convert post to postDtos
        PostResponse postresponse = new PostResponse();
        postresponse.setContent(postDtos);
        postresponse.setPageNumber(pagePost.getNumber());
        postresponse.setPageSize(pagePost.getSize());
        postresponse.setTotalElements((int) pagePost.getTotalElements());
        postresponse.setTotalPages(pagePost.getTotalPages());
        postresponse.setLastPage(pagePost.isLast());
        return postresponse; // return the postDtos at client
    }

    @Override
    public PostResponse getAllPostWithSortingByAndDir(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        //Sort sort = (SortdirortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending());
        // use ternary operator
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
//        Sort sort;
//        if(sortDir.equalsIgnoreCase("asc"))
//        {
//               sort=Sort.by(sortBy).ascending();
//        }else
//        {
//               sort=Sort.by(sortBy).descending();
//        }

        Pageable p = PageRequest.of(pageNumber, pageSize,sort);
        Page<Post> pagePost = this.postRepository.findAll(p); // pageable return the page
        List<Post> allposts = pagePost.getContent();  // page through we get the content and posts
        List<PostDto> postDtos = allposts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());   // convert post to postDtos
        PostResponse postresponse = new PostResponse();
        postresponse.setContent(postDtos);
        postresponse.setPageNumber(pagePost.getNumber());
        postresponse.setPageSize(pagePost.getSize());
        postresponse.setTotalElements((int) pagePost.getTotalElements());
        postresponse.setTotalPages(pagePost.getTotalPages());
        postresponse.setLastPage(pagePost.isLast());
        return postresponse; // return the postDtos at client
    }

    @Override
    public PostResponse getAllPostswithSortingAndOrderwithAppConstant(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
                /*
                if(sortDir.equalsIgnoreCase('asc')){
                sort=Sort.by(sortBy).ascending());
              }else
              {
               sort=Sort.by(sortBy).descending());
              }
               */
        Pageable p = PageRequest.of(pageNumber, pageSize,sort);
        Page<Post> pagePost = this.postRepository.findAll(p); // pageable return the page
        List<Post> allposts = pagePost.getContent();  // page through we get the content and posts
        List<PostDto> postDtos = allposts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());   // convert post to postDtos
        PostResponse postresponse = new PostResponse();
        postresponse.setContent(postDtos);
        postresponse.setPageNumber(pagePost.getNumber());
        postresponse.setPageSize(pagePost.getSize());
        postresponse.setTotalElements((int) pagePost.getTotalElements());
        postresponse.setTotalPages(pagePost.getTotalPages());
        postresponse.setLastPage(pagePost.isLast());
        return postresponse; // return the postDtos at client
    }


    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = this.postRepository.searchByTitle("%"+keyword+"%");
        List<PostDto> postDtoList = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }
}

