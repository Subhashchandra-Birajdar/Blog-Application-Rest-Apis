package com.Blog_App_Apis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
//@Builder
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;

        @Column(name = "user_name",nullable = false,length = 100)
        private String name;

        @Column(name = "user_email")
        private String email;

        @Column(name = "user_password")
        private String password;

        @Column(name = "user_about")
        private String about;
}
