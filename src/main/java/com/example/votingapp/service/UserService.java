package com.example.votingapp.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.votingapp.entity.User;
import com.example.votingapp.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addNew(User user) {
		User entity = new User();
		entity.setUsername(user.getUsername());
		entity.setPassword(user.getPassword());
		entity.setEmail(user.getEmail());
		entity.setPhone(user.getPhone());
		userRepository.save(entity);
		return true;
	}
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isUserExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
