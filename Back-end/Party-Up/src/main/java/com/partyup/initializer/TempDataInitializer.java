package com.partyup.initializer;

import com.partyup.model.*;
import com.partyup.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class TempDataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;


    @PostConstruct
    public void initialize() {
        List<Question> questions = questionsRepository.findAll();
        for (int i = 1; i <= 10; i++) {
            Player player = new Player();
            player.setUsername("User" + i);
            player.setFirstName("User " + i);
            player.setLastName("User " + i);
            player.setEmail("user" + i + "@example.com");
            player.setPassword(passwordEncoder.encode("1234"));
            player.setCountry(countryRepository.findById("Egypt").get());
            player.addRole(roleRepository.findByName("ROLE_USER").get());
            playerRepository.save(player);
            player = playerRepository.findByUsernameOrEmail(player.getUsername(), player.getUsername()).get();
            List<Rate> rates = new ArrayList<>();
            for (Question question: questions) {
                Rate rate = new Rate();
                rate.setPlayerID(player.getId());
                rate.setQuestionID(question.getId());
                Random random = new Random();
                rate.setRate(random.nextInt(5) + 1);
                rates.add(rate);
            }
            player.setRates(rates);
            Game lol = gameRepository.findByName("League Of Legends").get();
            Handle handle = new Handle();
            handle.setGame(lol);
            handle.setHandleName("LOL" + i);
            player.addHandle(handle);
            playerRepository.save(player);
        }
    }
}
