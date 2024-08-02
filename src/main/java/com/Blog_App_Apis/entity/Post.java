package com.Blog_App_Apis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name="post_title",length = 100,nullable = false)
    private String title;

    @Column(length = 1000)
    private String content;

    private String imageName;

    private Date addedDate;

    //implement the relation between the table
    //step - 3
    @ManyToOne
    @JoinColumn(name="categoryId")
    private Category category; // many post with one Category

    //step - 4
    @ManyToOne
    private User user; // many post with one user
}