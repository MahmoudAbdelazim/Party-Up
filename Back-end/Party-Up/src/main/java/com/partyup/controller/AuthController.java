package com.partyup.controller;

import com.partyup.model.Country;
import com.partyup.model.Player;
import com.partyup.model.Role;
import com.partyup.payload.LoginDto;
import com.partyup.payload.LoginResponseDto;
import com.partyup.payload.SignUpDto;
import com.partyup.repository.CountryRepository;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.RoleRepository;
import com.partyup.session.InMemorySessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InMemorySessionRegistry sessionRegistry;

    @Autowired
    private CountryRepository countryRepository;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> authenticateUser(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()));
        final String sessionId = sessionRegistry.registerSession(loginDto.getUsernameOrEmail());
        LoginResponseDto response = new LoginResponseDto();
        response.setSessionId(sessionId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto) {

        // add check for username exists in a DB
        if (playerRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if (playerRepository.existsByEmail(signUpDto.getEmail())) {
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

        Optional<Country> country = countryRepository.findById(signUpDto.getCountry().getName());
        if (country.isEmpty()) {
            Country c = new Country();
            c.setName(signUpDto.getCountry().getName());
            countryRepository.saveAndFlush(c);
        }
        player.setCountry(signUpDto.getCountry());

        Role role = roleRepository.findByName("ROLE_USER").get();
        player.setRoles(Collections.singleton(role));

        playerRepository.saveAndFlush(player);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}