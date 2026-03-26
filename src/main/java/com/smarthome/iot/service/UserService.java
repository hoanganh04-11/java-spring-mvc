package com.smarthome.iot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smarthome.iot.domain.User;
import com.smarthome.iot.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public User getUserById(long id){
        return this.userRepository.findById(id);
    }

    public User handleSaveUser(User user){
        User eric = this.userRepository.save(user);
        System.out.println(eric);
        return eric;
    }

}
