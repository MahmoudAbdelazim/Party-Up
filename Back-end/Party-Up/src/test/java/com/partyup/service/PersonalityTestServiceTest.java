package com.partyup.service;

import com.partyup.model.Player;
import com.partyup.model.Question;
import com.partyup.payload.QuestionDto;
import com.partyup.repository.PlayerRepository;
import com.partyup.repository.QuestionsRepository;
import com.partyup.repository.RateRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
class PersonalityTestServiceTest {

	static private List<Question> listOfQuestions;

	@Mock
	QuestionsRepository questionsRepository;

	@Mock
	PlayerRepository playerRepository;

	@InjectMocks
	PersonalityTestService personalityTestService;

	@BeforeAll
	static void initialize() {
		listOfQuestions = new ArrayList<>();
		for (int i = 0; i < 5; ++i) {
			Question q = new Question();
			q.setQuestionString("This is question number " + i + 1);
			q.setId((long) (i + 1));
			listOfQuestions.add(q);
		}
	}

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		when(playerRepository.save(any(Player.class))).thenReturn(new Player());
		when(questionsRepository.findAll()).thenReturn(listOfQuestions);
	}

	@Test
	void getAllQuestions() {
		List<QuestionDto> dtos = personalityTestService.getAllQuestions();
		for (int i=0;i<listOfQuestions.size(); ++i) {
			assertEquals(dtos.get(i).getId(), listOfQuestions.get(i).getId());
			assertEquals(dtos.get(i).getQuestion(), listOfQuestions.get(i).getQuestionString());
		}
	}

}