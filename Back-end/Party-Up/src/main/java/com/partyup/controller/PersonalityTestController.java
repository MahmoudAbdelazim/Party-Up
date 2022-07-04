package com.partyup.controller;

import com.partyup.model.Player;
import com.partyup.payload.AnswerDto;
import com.partyup.payload.QuestionDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.service.PersonalityTestService;
import com.partyup.service.exception.PlayerNotFoundException;
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
@CrossOrigin(origins = "*")
public class PersonalityTestController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PersonalityTestService personalityTestService;


    @GetMapping
    public ResponseEntity<List<QuestionDto>> getQuestions(){
        List<QuestionDto> questionList = personalityTestService.getAllQuestions();
        return ResponseEntity.ok().body(questionList);
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> saveAnswers(@PathVariable String username, @RequestBody List<AnswerDto> answerDto)
            throws PlayerNotFoundException {
        Optional<Player> player = playerRepository.findByUsernameOrEmail(username, username);
        if (player.isEmpty()) {
            throw new PlayerNotFoundException(username);
        }
        personalityTestService.saveAnswersOfUser(answerDto, player.get());
        return ResponseEntity.ok().body("Test Submitted Successfully");
    }
}