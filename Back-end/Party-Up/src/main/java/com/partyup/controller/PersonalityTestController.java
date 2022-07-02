package com.partyup.controller;

import com.partyup.model.Player;
import com.partyup.payload.QuestionDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.PersonalityTestService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personalityTest")
public class PersonalityTestController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PersonalityTestService personalityTestService;


    @GetMapping
    public ResponseEntity<List<QuestionDto>> getQuestions(){
        List<QuestionDto> questionList = personalityTestService.getAllQuestions();
        return ResponseEntity.status(HttpStatus.OK).body(questionList);
    }

    @PostMapping
    public ResponseEntity<String> saveAnswers(@RequestBody List<QuestionDto> questionDto){
        String username = getUserName();
        Optional<Player> player = playerRepository.findByUsernameOrEmail(username, username);
        if (player.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        personalityTestService.saveAnswersOfUser(questionDto, player.get());
        //TODO
        return ResponseEntity.ok().body("Test Submitted Successfully");
    }

    private String getUserName() {
        Object userSessionData = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (userSessionData instanceof UserDetails) {
            username = ((UserDetails) userSessionData).getUsername();
        } else {
            username = userSessionData.toString();
        }
        return username;
    }
}