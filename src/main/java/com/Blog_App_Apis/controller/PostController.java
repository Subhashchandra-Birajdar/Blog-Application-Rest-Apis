package com.Blog_App_Apis.controller;

import com.Blog_App_Apis.Payload.PostDto;
import com.Blog_App_Apis.Payload.PostResponse;
import com.Blog_App_Apis.config.AppConstants;
import com.Blog_App_Apis.exception.ApiResponse;
import com.Blog_App_Apis.service.FileService;
import com.Blog_App_Apis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postservice;
    @Autowired
    private FileService fileService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    // http://localhost:8080/api/posts/user/1/category/1/posts
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId)
    {
        PostDto postcreate = this.postservice.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(postcreate, HttpStatus.CREATED);
    }

    // api for delete the post
    @DeleteMapping("/postdelete/{postId}")
    // endpoint : http://localhost:8080/api/posts/postdelete/1
    public ApiResponse deletepost(
            @PathVariable("postId") Integer Id)
    {
        this.postservice.deletePost(Id);
        return new ApiResponse("Post is successfully deleted !!", true);
    }

    @GetMapping("/postgetall")
    // http://localhost:8080/api/posts/postgetall
    public ResponseEntity<List<PostDto>> getAllPosts()
    {
        List<PostDto> allPost = this.postservice.getAllPost();
        return new ResponseEntity<>(allPost, HttpStatus.OK);
    }

    // api for update the post
    @PutMapping("/postupdate/{postId}")
    // endpoint : http://localhost:8080/api/posts/postupdate/1
    public ResponseEntity<PostDto> updatepost(
            @RequestBody PostDto postDto,
            @PathVariable("postId") Integer Id)
    {
        PostDto updatedpostDto = this.postservice.updatePost(postDto, Id);
        return new ResponseEntity<PostDto>(updatedpostDto, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    // http://localhost:8080/api/posts/search/p
    public ResponseEntity<List<PostDto>> searchFunctionality(
            @PathVariable("keyword") String key)
    {
        List<PostDto> postDtos = this.postservice.searchPosts(key);
        return new ResponseEntity<>(postDtos, HttpStatus.ACCEPTED);
    }

    //get all the posts
    @GetMapping("/get-posts/Pagination-default")
    // http://localhost:8080/api/posts/get-posts/Pagination-default?pageNumber=0&pageSize=1
    public ResponseEntity<List<PostDto>> getAllPostsPaginationDefault(
            @RequestParam(value ="pageNumber",defaultValue="0", required=false) int pageNumber, // but always set default = 0
            @RequestParam(value ="pageSize",defaultValue="1", required=false) int pageSize) {    // but always set default = 10
        List<PostDto> allPosts = this.postservice.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    //get all the posts
    @GetMapping("/get-posts/Pagination")
    // http://localhost:8080/api/posts/get-posts/Pagination?pageNumber=0&pageSize=1
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam int pageNumber, // but always set default = 0
            @RequestParam int pageSize)
    {    // but always set default = 10
        List<PostDto> allPosts = this.postservice.getAllPosts(pageNumber, pageSize);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @GetMapping("/getallposts/PaginationWithPageResponse")
    // http://localhost:8080/api/posts/getallposts/PaginationWithPageResponse?pageNumber=0&pageSize=5
    public ResponseEntity<PostResponse> getAllPostsWithPageResponse(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize)
    {
        PostResponse postResponse = this.postservice.getAllPostsWithPageResponse(pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/getallposts/Order")
    //http://localhost:8080/api/posts/getallposts/Order?pageNumber=0&pageSize=5&sortBy=title&sortDir=desc
    public ResponseEntity<PostResponse> getAllPostswithSortingAndOrder(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber, // but always set default = 0
            @RequestParam(value = "pageSize", defaultValue = "2") int pageSize,    // but always set default = 10
            @RequestParam(value = "sortBy", defaultValue = "postId") String sortBy,    // but always set default = postId
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir)    // but always set default = sortDir
    {
        PostResponse postResponse = this.postservice.getAllPostWithSortingByAndDir(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }
    // Ratnamanikyam Manikyam, Ankit Bhardwaj
    @GetMapping("/getallposts/AppConstant")
    //http://localhost:8080/api/posts/getallposts/AppConstant?pageSize=5&sortBy=title&sortDir=desc
    public ResponseEntity<PostResponse> getAllPostswithSortingAndOrderwithAppConstant(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, // but always set default = 0
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,    // but always set default = 10
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,    // but always set default = postId
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)    // but always set default = sortDir
    {
        PostResponse postResponse = this.postservice.getAllPostswithSortingAndOrderwithAppConstant(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }


    //    @Value("${project.image}")
//    private String path;       // this annotation come from the lombok validation

    @Value("${project.image}")
    private String path;        // this comes from bean spring

    //upload the image to the post  , http://localhost:8080/api/posts/post/image/upload/1 add the file also,
    // in postman select form-data in write image and select the path then hit send it will update the post
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @PathVariable("postId") Integer postId,
            @RequestParam("image") MultipartFile image) throws IOException
    {
        PostDto postDto = this.postservice.getpostById(postId);
        String fileName = this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postservice.updatePost(postDto,postId);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    //method to serve files
//    @GetMapping(value="/image/downloadpost/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
//    // http://localhost:8080/api/posts/image/downloadpost/1
//   // @GetMapping(value = "/image/downloadpost/{imageName:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
//    public void downloadImage(
//            @PathVariable("imageName")String imageName,
//            HttpServletResponse response)throws IOException
//    {
//        InputStream resource = this.fileservice.getResource(path,imageName);
//        //response.getContentType(MediaType.IMAGE_JPEG_VALUE);
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(resource,response.getOutputStream());
//    }

//first get the image though id /// http://localhost:8080/api/posts/1
// http://localhost:8080/api/posts/image/downloadpost/imagenameaddhere .. get in crome

}


