package com.Blog_App_Apis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name="title",length=100,nullable=false)
    private String categoryTitle;

    @Column(name="description")
    private String categoryDescription;

    //change the category table step - 1
    @OneToMany(mappedBy = "category",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    // if parent is deleted then child also delete
    private List<Post> posts= new ArrayList<>();
}
