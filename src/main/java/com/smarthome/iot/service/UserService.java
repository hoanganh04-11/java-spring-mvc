package com.smarthome.iot.service;

import java.util.List;



import org.springframework.stereotype.Service;

import com.smarthome.iot.domain.Role;
import com.smarthome.iot.domain.User;
import com.smarthome.iot.domain.dto.RegisterDTO;
import com.smarthome.iot.repository.RoleRepository;
import com.smarthome.iot.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // public List<User> getAllUsersByEmail(String email) {
    //     return this.userRepository.findOneByEmail(email);
    // }

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User handleSaveUser(User user) {
        User eric = this.userRepository.save(user);
        return eric;
    }

    public void deleteAUser(Long id){
        this.userRepository.deleteById(id);
    }

    public Role getRoleByName(String name){
        return this.roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO){
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " +registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExist(String email){
        return this.userRepository.existsByEmail(email);
    }


    public User getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public long countUser(){
        return this.userRepository.count();
    }
}
