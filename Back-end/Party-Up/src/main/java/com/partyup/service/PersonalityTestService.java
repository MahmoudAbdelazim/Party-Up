package com.partyup.service;

import com.partyup.model.Player;
import com.partyup.model.Question;
import com.partyup.model.Rate;
import com.partyup.payload.QuestionDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.QuestionsRepository;
import com.partyup.repository.RateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalityTestService {

    private QuestionsRepository questionsRepository;

    private RateRepository rateRepository;

    private PlayerRepository playerRepository;

    @Autowired
    public PersonalityTestService(QuestionsRepository questionsRepository, RateRepository rateRepository, PlayerRepository playerRepository) {
        this.questionsRepository = questionsRepository;
        this.rateRepository = rateRepository;
        this.playerRepository = playerRepository;
    }

    public List<QuestionDto> getAllQuestions() {
        List<Question> listOfQuestions = questionsRepository.findAll();
        List<QuestionDto> dtoList = new ArrayList<>();
        for (Question question: listOfQuestions) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setId(question.getId());
            dtoList.add(questionDto);
        }
        return dtoList;
    }

    public void saveAnswersOfUser(List<QuestionDto> questionDtos, Player player) {
        List<Rate> rates = new ArrayList<>();
        for (int i = 0; i < questionDtos.size(); i++) {
            Rate rate = new Rate();
            QuestionDto questionDto;
            questionDto = questionDtos.get(i);
            rate.setQuestionID(questionDto.getId());
            rate.setPlayerID(player.getId());
            rate.setRate(questionDto.getAnswer());
            rateRepository.save(rate);
            rates.add(rate);
        }
        player.setRates(rates);
        playerRepository.save(player);
    }
}

@Configuration
class configPersonalityTestService{
    @Bean
    ModelMapper personalityTestMapper() {
        //TODO
        return null;
    }
}