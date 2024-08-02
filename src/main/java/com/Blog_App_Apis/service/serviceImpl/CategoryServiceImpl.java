package com.Blog_App_Apis.service.serviceImpl;

import com.Blog_App_Apis.Payload.CategoryDto;
import com.Blog_App_Apis.entity.Category;
import com.Blog_App_Apis.repository.CategoryRepository;
import com.Blog_App_Apis.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService
{

    @Autowired
    private CategoryRepository categoryrepository;

    @Autowired
    private ModelMapper modelmapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto)
    {
        Category category = this.modelmapper.map(categoryDto, Category.class);
        Category saved = this.categoryrepository.save(category);
        return this.modelmapper.map(saved, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId)
    {
        Category category = this.categoryrepository.findById(categoryId).get();
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updateCategory = this.categoryrepository.save(category);

        return this.modelmapper.map(updateCategory,CategoryDto.class);
    }
    @Override
    public void deleteCategory(Integer categoryId)
    {
        Category category = this.categoryrepository.findById(categoryId).get();
        this.categoryrepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId)
    {
        Category category = this.categoryrepository.findById(categoryId).get();
        return this.modelmapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories()
    {
        List<Category> all = this.categoryrepository.findAll();
        List<CategoryDto> CategoryDtos = all.stream()
                .map((category) -> this.modelmapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return CategoryDtos;
    }
}

