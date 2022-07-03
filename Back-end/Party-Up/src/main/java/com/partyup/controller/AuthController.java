package com.partyup.controller;

import com.partyup.model.Player;
import com.partyup.model.Role;
import com.partyup.payload.LoginDto;
import com.partyup.payload.SignUpDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(playerRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(playerRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        Player player = new Player();
        player.setFirstName(signUpDto.getFirstName());
        player.setLastName(signUpDto.getLastName());
        player.setUsername(signUpDto.getUsername());
        player.setEmail(signUpDto.getEmail());
        player.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        player.setPhoneNumber(signUpDto.getPhoneNumber());
        player.setState(signUpDto.getState());

        Role role = roleRepository.findByName("ROLE_USER").get();
        player.setRoles(Collections.singleton(role));

        System.out.println(player);
        playerRepository.saveAndFlush(player);

//        LoginDto loginDto = new LoginDto();
//        loginDto.setUsernameOrEmail(player.getUsername());
//        loginDto.setPassword(player.getPassword());
//        authenticateUser(loginDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}