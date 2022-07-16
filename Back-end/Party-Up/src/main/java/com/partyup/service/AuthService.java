package com.partyup.service;

import com.partyup.model.Country;
import com.partyup.model.Player;
import com.partyup.model.Role;
import com.partyup.payload.LoginDto;
import com.partyup.payload.LoginResponseDto;
import com.partyup.payload.SignUpDto;
import com.partyup.repository.CountryRepository;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.RoleRepository;
import com.partyup.service.exception.UserNotAuthenticatedException;
import com.partyup.session.InMemorySessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {
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

    public AuthService(AuthenticationManager authenticationManager, PlayerRepository playerRepository,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                       InMemorySessionRegistry sessionRegistry, CountryRepository countryRepository) {
        this.authenticationManager = authenticationManager;
        this.playerRepository = playerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionRegistry = sessionRegistry;
        this.countryRepository = countryRepository;
    }

    public AuthService() {
    }

    public LoginResponseDto signin(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()));
        final String sessionId = sessionRegistry.registerSession(loginDto.getUsernameOrEmail());
        LoginResponseDto response = new LoginResponseDto();
        response.setSessionId(sessionId);
        return response;
    }

    public ResponseEntity<String> register(SignUpDto signUpDto) {
        if (playerRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (playerRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }
        if (playerRepository.existsByDiscordTag(signUpDto.getDiscordTag())) {
            return new ResponseEntity<>("Discord Tag is already taken!", HttpStatus.BAD_REQUEST);
        }
        Player player = new Player();
        player.setFirstName(signUpDto.getFirstName());
        player.setLastName(signUpDto.getLastName());
        player.setUsername(signUpDto.getUsername());
        player.setEmail(signUpDto.getEmail());
        player.setDiscordTag(signUpDto.getDiscordTag());
        player.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

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
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }

    public Player authenticate() throws UserNotAuthenticatedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.isAuthenticated()) {
            throw new UserNotAuthenticatedException();
        }
        String username = getUsername(auth);
        Player player = playerRepository.findByUsernameOrEmail(username, username).get();
        return player;
    }

    private String getUsername(Authentication authentication) {
        Object userSessionData = authentication.getPrincipal();
        String username;
        if (userSessionData instanceof UserDetails) {
            username = ((UserDetails) userSessionData).getUsername();
        } else {
            username = userSessionData.toString();
        }
        return username;
    }
}
