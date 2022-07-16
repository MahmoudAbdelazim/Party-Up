package com.partyup.service;

import com.partyup.model.Player;
import com.partyup.model.Question;
import com.partyup.model.Rate;
import com.partyup.payload.AnswerDto;
import com.partyup.payload.QuestionDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalityTestService {

    private QuestionsRepository questionsRepository;

    private PlayerRepository playerRepository;

    @Autowired
    public PersonalityTestService(QuestionsRepository questionsRepository, PlayerRepository playerRepository) {
        this.questionsRepository = questionsRepository;
        this.playerRepository = playerRepository;
    }

    public List<QuestionDto> getAllQuestions() {
        List<Question> listOfQuestions = questionsRepository.findAll();
        List<QuestionDto> dtoList = new ArrayList<>();
        for (Question question: listOfQuestions) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setId(question.getId());
            questionDto.setQuestion(question.getQuestionString());
            dtoList.add(questionDto);
        }
        return dtoList;
    }

    public void saveAnswersOfUser(List<AnswerDto> answerDtos, Player player) {
        List<Rate> rates = new ArrayList<>();
        for (AnswerDto dto : answerDtos) {
            Rate rate = new Rate();
            AnswerDto answerDto;
            answerDto = dto;
            rate.setQuestionID(answerDto.getId());
            rate.setPlayerID(player.getId());
            rate.setRate(answerDto.getAnswer());
            rates.add(rate);
        }
        player.setRates(rates);
        playerRepository.save(player);
    }
}