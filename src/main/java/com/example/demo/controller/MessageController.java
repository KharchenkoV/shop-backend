package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Message;
import com.example.demo.repository.MessageRepository;

@RestController
@RequestMapping("/api/v1/")
public class MessageController {
	
	@Autowired
	private MessageRepository messageRepository;
	
	@GetMapping("/messages")
	public List<Message> getAllMessages(){
		return messageRepository.findAll();
	}
	
	@PostMapping("/message")
	public Message createMessage(@RequestBody Message message) {
		return messageRepository.save(message);
	}
}
