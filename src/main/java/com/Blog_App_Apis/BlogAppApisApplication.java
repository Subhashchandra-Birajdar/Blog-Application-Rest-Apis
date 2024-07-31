package com.Blog_App_Apis;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogAppApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}

/*
ModelMapper:
ModelMapper is to map objects by determining how one object model is mapped to another called a Data Transformation Object (DTO).
use of ModelMapper is user convert one obj form to another object
 */