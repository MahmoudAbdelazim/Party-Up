package com.partyup.service;

import com.partyup.model.Country;
import com.partyup.model.Game;
import com.partyup.model.Handle;
import com.partyup.model.Player;
import com.partyup.payload.OtherProfileDto;
import com.partyup.payload.OwnProfileDto;
import com.partyup.payload.SignUpDto;
import com.partyup.repository.ContentRepository;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.exception.PlayerNotFoundException;
import com.partyup.service.exception.UploadFailedException;
import com.partyup.service.exception.UserNotAuthenticatedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PlayerProfileServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ContentRepository contentRepo;
    @Mock
    private AuthService authService;
    @InjectMocks
    private PlayerProfileService playerProfileService;

    @BeforeEach
    void init() throws UserNotAuthenticatedException {
        MockitoAnnotations.openMocks(this);
        when(authService.authenticate()).thenReturn(new Player());
    }

    @Test
    void getPlayerProfile() throws UserNotAuthenticatedException {
        Player player = new Player();
        player.setFirstName("First");
        player.setLastName("Last");
        player.setEmail("email@email.com");
        player.setUsername("username");
        player.setDiscordTag("disc#1234");
        Country country = new Country();
        country.setName("Egypt");
        player.setCountry(country);
        Game game1 = new Game();
        game1.setName("League Of Legends");
        Handle handle1 = new Handle();
        handle1.setGame(game1);
        handle1.setHandleName("Handle1");
        List<Handle> handles = new ArrayList<>();
        handles.add(handle1);
        player.setHandles(handles);
        when(authService.authenticate()).thenReturn(player);
        OwnProfileDto ownProfileDto = playerProfileService.getPlayerProfile();
        assertEquals(ownProfileDto.getFirstName(), player.getFirstName());
        assertEquals(ownProfileDto.getLastName(), player.getLastName());
        assertEquals(ownProfileDto.getUsername(), player.getUsername());
        assertEquals(ownProfileDto.getEmail(), player.getEmail());
        assertEquals(ownProfileDto.getDiscordTag(), player.getDiscordTag());
        assertEquals(ownProfileDto.getCountry(), player.getCountry());
        assertEquals(ownProfileDto.getHandles().get(0).getHandle(), player.getHandles().get(0).getHandleName());
    }

    @Test
    void editProfile() throws UserNotAuthenticatedException {
        when(playerRepository.findAllByUsername(any())).thenReturn(List.of(new Player()));
        when(playerRepository.findAllByEmail(any())).thenReturn(List.of(new Player()));
        when(playerRepository.findAllByDiscordTag(any())).thenReturn(List.of(new Player()));
        when(passwordEncoder.encode(any())).thenReturn("1234");
        when(playerRepository.saveAndFlush(any())).thenReturn(null);
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setFirstName("First");
        signUpDto.setLastName("Last");
        signUpDto.setDiscordTag("Disc#1234");
        signUpDto.setPassword("1234");
        signUpDto.setEmail("user@example.com");
        signUpDto.setUsername("user");
        Country country = new Country();
        country.setName("Egypt");
        signUpDto.setCountry(country);

        assertEquals(playerProfileService.editProfile(signUpDto), "Profile Edited Successfully");
    }

    @Test
    void editProfileWithExistingUsername() throws UserNotAuthenticatedException {
        when(playerRepository.findAllByUsername(any())).thenReturn(List.of(new Player(), new Player()));
        when(playerRepository.findAllByEmail(any())).thenReturn(List.of(new Player()));
        when(playerRepository.findAllByDiscordTag(any())).thenReturn(List.of(new Player()));
        when(passwordEncoder.encode(any())).thenReturn("1234");
        when(playerRepository.saveAndFlush(any())).thenReturn(null);
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setFirstName("First");
        signUpDto.setLastName("Last");
        signUpDto.setDiscordTag("Disc#1234");
        signUpDto.setPassword("1234");
        signUpDto.setEmail("user@example.com");
        signUpDto.setUsername("user");
        Country country = new Country();
        country.setName("Egypt");
        signUpDto.setCountry(country);

        assertEquals(playerProfileService.editProfile(signUpDto), "Username is already taken!");
    }

    @Test
    void editProfileWithExistingEmail() throws UserNotAuthenticatedException {
        when(playerRepository.findAllByUsername(any())).thenReturn(List.of(new Player()));
        when(playerRepository.findAllByEmail(any())).thenReturn(List.of(new Player(), new Player()));
        when(playerRepository.findAllByDiscordTag(any())).thenReturn(List.of(new Player()));
        when(passwordEncoder.encode(any())).thenReturn("1234");
        when(playerRepository.saveAndFlush(any())).thenReturn(null);
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setFirstName("First");
        signUpDto.setLastName("Last");
        signUpDto.setDiscordTag("Disc#1234");
        signUpDto.setPassword("1234");
        signUpDto.setEmail("user@example.com");
        signUpDto.setUsername("user");
        Country country = new Country();
        country.setName("Egypt");
        signUpDto.setCountry(country);

        assertEquals(playerProfileService.editProfile(signUpDto), "Email is already taken!");
    }

    @Test
    void editProfileWithExistingDiscordTag() throws UserNotAuthenticatedException {
        when(playerRepository.findAllByUsername(any())).thenReturn(List.of(new Player()));
        when(playerRepository.findAllByEmail(any())).thenReturn(List.of(new Player()));
        when(playerRepository.findAllByDiscordTag(any())).thenReturn(List.of(new Player(), new Player()));
        when(passwordEncoder.encode(any())).thenReturn("1234");
        when(playerRepository.saveAndFlush(any())).thenReturn(null);
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setFirstName("First");
        signUpDto.setLastName("Last");
        signUpDto.setDiscordTag("Disc#1234");
        signUpDto.setPassword("1234");
        signUpDto.setEmail("user@example.com");
        signUpDto.setUsername("user");
        Country country = new Country();
        country.setName("Egypt");
        signUpDto.setCountry(country);

        assertEquals(playerProfileService.editProfile(signUpDto), "Discord Tag is already taken!");
    }

    @Test
    void getOtherPlayerProfilePeer()
            throws UserNotAuthenticatedException, PlayerNotFoundException {
        Player player1 = new Player();
        Player player2 = new Player();
        player2.setHandles(new ArrayList<>());
        player2.setReviewers(new ArrayList<>());
        player2.setDiscordTag("disc#1234");
        player1.addPeer(player2);
        player2.addPeer(player1);
        when(playerRepository.findByUsernameOrEmail(any(), anyString())).thenReturn(Optional.of(player2));
        when(authService.authenticate()).thenReturn(player1);
        OtherProfileDto otherProfileDto = playerProfileService.getOtherPlayerProfile("OtherPlayer");
        assertTrue(otherProfileDto.isPeer());
        assertEquals(otherProfileDto.getDiscordTag(), player2.getDiscordTag());
    }

    @Test
    void getOtherPlayerProfileNonPeer()
            throws UserNotAuthenticatedException, PlayerNotFoundException {
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setPeers(new HashSet<>());
        player1.setPeerRequests(new ArrayList<>());
        player2.setHandles(new ArrayList<>());
        player2.setReviewers(new ArrayList<>());
        player2.setDiscordTag("disc#1234");
        player2.setPeerRequests(new ArrayList<>());
        when(playerRepository.findByUsernameOrEmail(any(), anyString())).thenReturn(Optional.of(player2));
        when(authService.authenticate()).thenReturn(player1);
        OtherProfileDto otherProfileDto = playerProfileService.getOtherPlayerProfile("OtherPlayer");
        assertFalse(otherProfileDto.isPeer());
        assertNull(otherProfileDto.getDiscordTag());
    }
}