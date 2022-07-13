package com.example.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.models.User;

public interface UserService extends UserDetailsService{
	User findByUsername(String name);
	void save(User user);
}
