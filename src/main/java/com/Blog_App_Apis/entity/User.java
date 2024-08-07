package com.Blog_App_Apis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
//@Builder
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(name = "user_name",nullable = false,length = 100)
        private String name;

        @Column(name = "user_email")
        private String email;

        @Column(name = "user_password")
        private String password;

        @Column(name = "user_about")
        private String about;

        // *2
        @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY) // if parent is deleted then child also delete
        private List<Post> posts= new ArrayList<>();
}
