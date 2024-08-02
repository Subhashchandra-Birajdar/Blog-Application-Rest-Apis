package com.Blog_App_Apis.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    @NotBlank
    @Size(min=4,message = "Min size of cateogry title is 4")
    private String categoryTitle;

    @NotBlank
    @Size(min=10,message="min size of category desc is 10")
    private String categoryDescription;
}
