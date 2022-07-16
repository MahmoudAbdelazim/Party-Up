package com.partyup.service;

import com.partyup.model.Country;
import com.partyup.model.Player;
import com.partyup.model.Role;
import com.partyup.payload.LoginDto;
import com.partyup.payload.LoginResponseDto;
import com.partyup.payload.SignUpDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.partyup.repository.CountryRepository;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.RoleRepository;
import com.partyup.service.exception.UserNotAuthenticatedException;
import com.partyup.session.InMemorySessionRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.naming.AuthenticationException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private InMemorySessionRegistry sessionRegistry;
    @Mock
    private CountryRepository countryRepository;

    Player player;
    SignUpDto signUpDto;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        player = new Player();
        player.setFirstName("First");
        player.setLastName("Last");
        player.setDiscordTag("Disc#1234");
        player.setPassword(passwordEncoder.encode("1234"));
        player.setEmail("user@example.com");
        player.setUsername("user");

        signUpDto = new SignUpDto();
        signUpDto.setFirstName("First");
        signUpDto.setLastName("Last");
        signUpDto.setDiscordTag("Disc#1234");
        signUpDto.setPassword("1234");
        signUpDto.setEmail("user@example.com");
        signUpDto.setUsername("user");
        Country country = new Country();
        country.setName("Egypt");
        signUpDto.setCountry(country);

        when(passwordEncoder.encode(any())).thenReturn("1234");
        when(countryRepository.findById(any())).thenReturn(Optional.empty());
        when(countryRepository.saveAndFlush(any())).thenReturn(null);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(new Role()));
        when(playerRepository.saveAndFlush(any())).thenReturn(null);
    }

    @Test
    void registerWithUniqueData() {
        when(playerRepository.existsByUsername(any())).thenReturn(false);
        when(playerRepository.existsByEmail(any())).thenReturn(false);
        when(playerRepository.existsByDiscordTag(any())).thenReturn(false);

        ResponseEntity<String> response = authService.register(signUpDto);
        assertEquals(response.getBody(), "User Registered Successfully");
    }

    @Test
    void registerWithExistingUsername() {
        when(playerRepository.existsByUsername(any())).thenReturn(true);
        when(playerRepository.existsByEmail(any())).thenReturn(false);
        when(playerRepository.existsByDiscordTag(any())).thenReturn(false);

        ResponseEntity<String> response = authService.register(signUpDto);
        assertEquals(response.getBody(), "Username is already taken!");
    }

    @Test
    void registerWithExistingEmail() {
        when(playerRepository.existsByUsername(any())).thenReturn(false);
        when(playerRepository.existsByEmail(any())).thenReturn(true);
        when(playerRepository.existsByDiscordTag(any())).thenReturn(false);

        ResponseEntity<String> response = authService.register(signUpDto);
        assertEquals(response.getBody(), "Email is already taken!");
    }

    @Test
    void registerWithExistingDiscordTag() {
        when(playerRepository.existsByUsername(any())).thenReturn(false);
        when(playerRepository.existsByEmail(any())).thenReturn(false);
        when(playerRepository.existsByDiscordTag(any())).thenReturn(true);

        ResponseEntity<String> response = authService.register(signUpDto);
        assertEquals(response.getBody(), "Discord Tag is already taken!");
    }

    @Test
    void signinWithCorrectData() {
        when(sessionRegistry.registerSession(any())).thenReturn("1");
        when(authenticationManager.authenticate(any())).thenReturn(null);
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("user");
        loginDto.setPassword("pass");
        assertEquals(authService.signin(loginDto).getSessionId(), "1");
    }

    @Test
    void signinWithIncorrectData() {
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException());
        LoginDto loginDto = new LoginDto();
        loginDto.setUsernameOrEmail("user");
        loginDto.setPassword("pass");
        assertThrows(RuntimeException.class, () -> authService.signin(loginDto));
    }
}