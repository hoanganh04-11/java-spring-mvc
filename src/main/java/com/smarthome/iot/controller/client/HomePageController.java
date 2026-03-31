package com.smarthome.iot.controller.client;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smarthome.iot.domain.User;
import com.smarthome.iot.domain.dto.RegisterDTO;
import com.smarthome.iot.service.UserService;

@Controller
public class HomePageController {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getHomePage(){
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/login";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") RegisterDTO registerDTO){
        User user = this.userService.registerDTOtoUser(registerDTO);   

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));
        this.userService.handleSaveUser(user);

        return "redirect:/login";
    }
}
