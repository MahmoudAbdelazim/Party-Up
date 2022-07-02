package com.partyup.service;

import com.partyup.model.Player;
import com.partyup.model.Question;
import com.partyup.model.Rate;
import com.partyup.payload.QuestionDto;
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

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private RateRepository rateRepository;

    public List<QuestionDto> getAllQuestions() {
        List<Question> listOfQuestions = questionsRepository.findAll();
        return new ArrayList<QuestionDto>();
    }

    public void saveAnswersOfUser(List<QuestionDto> questionDtos, Player player) {
        for (int i = 0; i < questionDtos.size(); i++) {
            Rate rate = new Rate();
            QuestionDto questionDto;
            questionDto = questionDtos.get(i);
            rate.setQuestionID(questionDto.getId());
            rate.setPlayerID(player.getId());
            rate.setRate(questionDto.getAnswer());
            rateRepository.save(rate);
        }
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