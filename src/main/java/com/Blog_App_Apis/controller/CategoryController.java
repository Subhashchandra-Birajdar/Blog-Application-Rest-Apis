package com.Blog_App_Apis.controller;

import com.Blog_App_Apis.Payload.CategoryDto;
import com.Blog_App_Apis.exception.ApiResponse;
import com.Blog_App_Apis.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController
{

    @Autowired
    private CategoryService categoryService;       // http://localhost:8080/api/categories/

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createcategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createcategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
          @Valid @RequestBody CategoryDto categoryDto,
            @PathVariable Integer categoryId)
    {
        CategoryDto updatecategory = this.categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<CategoryDto>(updatecategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<  ApiResponse> deleteCategory(
            @PathVariable Integer categoryId)
    {
        this.categoryService.deleteCategory(categoryId);
        return  new ResponseEntity<ApiResponse>
                (new ApiResponse(
                        "category is deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(
            @PathVariable Integer categoryId)
    {
        CategoryDto getcategorydto = this.categoryService.getCategory(categoryId);
        return  new ResponseEntity<>(getcategorydto,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories()
    {
        List<CategoryDto> allcategories = this.categoryService.getCategories();
        return  new ResponseEntity<>(allcategories,HttpStatus.OK);
    }
}


