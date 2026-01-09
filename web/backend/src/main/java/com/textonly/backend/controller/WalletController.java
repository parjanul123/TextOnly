package com.textonly.backend.controller;

import com.textonly.backend.model.User;
import com.textonly.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class WalletController {
    @Autowired
    private UserRepository userRepository;



}
