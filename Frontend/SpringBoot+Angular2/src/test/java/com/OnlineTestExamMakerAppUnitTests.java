package com;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.domain.Examparameters;
import com.domain.Multiplechoiceanswers;
import com.domain.Questions;
import com.domain.Testexam;
import com.domain.User;
import com.service.QuestionService;
import com.service.RestDataRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineTestExamMakerAppUnitTests {

	@Autowired
	private QuestionService questionService;

	@MockBean
	private RestDataRepository restDataRepository;
	
	TestData testdata;

	@Before
	public void setUp(){
		testdata = new TestData();
	}
	
	//
	@Test
	public void testgetUserByUserNameExist() throws Exception {
		when(this.restDataRepository.getUserByUserName("vdwoodie")).thenReturn(new User(1, "vdwoodie", "Victor Woodrow", "123456"));
		User user = questionService.getUserByUserName("vdwoodie");
		assertThat(user.getUsername()).isEqualTo("vdwoodie");
		assertThat(user.getFullname()).isEqualTo("Victor Woodrow");    
	}
	@Test
	public void testgetUserByUserNameNotExist() throws Exception {
		when(this.restDataRepository.getUserByUserName("testMathew")).thenReturn(new User());
		User user = questionService.getUserByUserName("testMathew");
		assertThat(user.getUsername()).isNull();
		assertThat(user.getFullname()).isNull();    
	}
	
	@Test
	public void testgetTestExamByUserNameExist() throws Exception {
		when(this.restDataRepository.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
		Testexam testexam = questionService.getTestExamByUserName("vdwoodie");
		assertThat(testexam.getUsername()).isEqualTo("vdwoodie");
		assertThat(testexam.getExamcode()).isEqualTo("C0001");
	}

	@Test
	public void testgetTestExamByUserNameNotExist() throws Exception {
		when(this.restDataRepository.getTestExamByUserName("testmathew")).thenReturn(new Testexam());
		Testexam testexam = questionService.getTestExamByUserName("testmathew");
		assertThat(testexam.getUsername()).isNull();
		assertThat(testexam.getExamcode()).isNull();   
	}

	@Test
	public void testgetQuestionsListByUserNameExist() throws Exception {
		when(this.restDataRepository.getQuestionsListByUserName("vdwoodie")).thenReturn(testdata.getMultipleQuestions("vdwoodie"));
		List<Questions> questions = questionService.getQuestionsListByUserName("vdwoodie");
		assertThat(questions.size()).isGreaterThan(0);
		assertThat(questions.get(1).getUsername()).isEqualTo("vdwoodie");
	}

	@Test
	public void testgetQuestionsListByUserNameNotExist() throws Exception {
		when(this.restDataRepository.getQuestionsListByUserName("vdwoodie")).thenReturn(testdata.getMultipleQuestions("testmathew"));
		List<Questions> questions = questionService.getQuestionsListByUserName("testmathew");
		assertThat(questions.size()).isEqualTo(0);
	}
	//
	@Test
	public void testgetAnswersByUsernameExist() throws Exception {
		when(this.restDataRepository.getAnswersByUsername("vdwoodie")).thenReturn(testdata.generateMultipleChoice("vdwoodie"));
		Map<String, List<Multiplechoiceanswers>> answers = questionService.getAnswersByUsername("vdwoodie");
		assertThat(answers.size()).isGreaterThan(0);
		assertThat(answers.get("Q4").size()).isGreaterThan(0);
	}

	@Test
	public void testgetAnswersByUsernameNotExist() throws Exception {
		when(this.restDataRepository.getAnswersByUsername("testmathew")).thenReturn(testdata.generateMultipleChoice("testmathew"));
		Map<String, List<Multiplechoiceanswers>> answers = questionService.getAnswersByUsername("testmathew");
		assertThat(answers.size()).isEqualTo(0);
	}
	@Test
	public void testgetExamParameterByExamCodeExist() throws Exception {
		when(this.restDataRepository.getExamParameterByExamCode("C0001")).thenReturn(new Examparameters(1, "C0001", "Test Exam Maker", "This exam is a Crossover exam test for selction of candidiate to positions within crossover"));
		Examparameters examparameters = questionService.getExamParameterByExamCode("C0001");
		assertThat(examparameters.getExamcode()).isEqualTo("C0001");
		assertThat(examparameters.getExamname()).contains("Test Exam Maker");
	}

	@Test
	public void testgetExamParameterByExamCodeNotExist() throws Exception {
		when(this.restDataRepository.getExamParameterByExamCode("C0002")).thenReturn(new Examparameters());
		Examparameters examparameters = questionService.getExamParameterByExamCode("C0002");
		assertThat(examparameters.getExamcode()).isNull();
		assertThat(examparameters.getExamname()).isNull();   
	}
	
	@Test
	public void testLoadCandidateExamUserNameExist() throws Exception {
		when(this.restDataRepository.getAnswersByUsername("vdwoodie")).thenReturn(testdata.generateMultipleChoice("vdwoodie"));
		when(this.restDataRepository.getQuestionsListByUserName("vdwoodie")).thenReturn(testdata.getMultipleQuestions("vdwoodie"));
		questionService.LoadCandidateExam("vdwoodie");
		assertThat(questionService.getAllcandidateinfo().get("vdwoodie")).isEqualTo(0);
		assertThat(questionService.getAllcandidateScore().get("vdwoodie")).isEqualTo(0);
		assertThat(questionService.getAllcandidateQuestions().get("vdwoodie").size()).isGreaterThan(0);
		assertThat(questionService.getAllMultipleChoiceAnswers().get("vdwoodie").get("Q4").size()).isGreaterThan(0);
	}

	@Test
	public void testLoadCandidateExamUsernamNotExist() throws Exception {
		when(this.restDataRepository.getAnswersByUsername("testmathew")).thenReturn(testdata.generateMultipleChoice("testmathew"));
		when(this.restDataRepository.getQuestionsListByUserName("testmathew")).thenReturn(testdata.getMultipleQuestions("testmathew"));
		questionService.LoadCandidateExam("testmathew");
		assertNull(questionService.getAllcandidateinfo().get("testmathew"));
		assertNull(questionService.getAllcandidateScore().get("testmathew"));
		assertNull(questionService.getAllcandidateQuestions().get("testmathew")); 
		assertNull(questionService.getAllMultipleChoiceAnswers().get("testmathew")); 
	}
}
