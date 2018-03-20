package com;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.domain.Testexam;
import com.service.QuestionService;
import com.service.RestDataRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineTestExamMakerAppUtilityUnitTests {
	protected Logger logger = Logger.getLogger(OnlineTestExamMakerAppUtilityUnitTests.class.getName());
	@Autowired
	private QuestionService questionService;

	@MockBean
	private RestDataRepository restDataRepository;
	
	TestData testdata;

	@Before
	public void setUp(){
		testdata = new TestData();
	}
	
	@Test
	public void testUtilityFunctionsPass() throws Exception {
		when(this.restDataRepository.getAnswersByUsername("vdwoodie")).thenReturn(testdata.generateMultipleChoice("vdwoodie"));
		when(this.restDataRepository.getQuestionsListByUserName("vdwoodie")).thenReturn(testdata.getMultipleQuestions("vdwoodie"));
		when(this.restDataRepository.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
		questionService.LoadCandidateExam("vdwoodie");
		questionService.setQuestionNo("vdwoodie", 1);
		questionService.updateAnswers("vdwoodie", AnswerQuestion(1));
		assertThat(questionService.getQuestionNo("vdwoodie")).isEqualTo(1);
		questionService.setQuestionNo("vdwoodie", 10);
		questionService.updateAnswers("vdwoodie", AnswerQuestion(10));
		assertThat(questionService.getQuestionNo("vdwoodie")).isEqualTo(10);
		questionService.setQuestionNo("vdwoodie", 25);
		questionService.updateAnswers("vdwoodie", AnswerQuestion(25));
		//logger.info("testUtilityFunctionsPass() invoked: for getQuestionNo :- " + questionService.getQuestionNo("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for CountAllQuestions :- " + questionService.CountAllQuestions("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for getQuestion, Number :- " + questionService.getQuestion("vdwoodie").getDisplayorder());
		//logger.info("testUtilityFunctionsPass() invoked: for unAnswered (T/F) :- " + questionService.unAnsweredQuestions("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for getAnswers (Q1, A) :- " + questionService.getAnswers("vdwoodie", "Q1").get("B").getCandidateanswer());

		assertThat(questionService.getQuestionNo("vdwoodie")).isEqualTo(25);
		assertThat(questionService.CountAllQuestions("vdwoodie")).isEqualTo(25);
		assertThat(questionService.getQuestion("vdwoodie").getDisplayorder()).isEqualTo(25);
		assertTrue(questionService.unAnsweredQuestions("vdwoodie"));
		assertTrue(questionService.getAnswers("vdwoodie", "Q1").get("A").getCandidateanswer());
		//logger.info("testUtilityFunctionsPass() invoked: for examClosingUpdate :- " + questionService.examClosingUpdate("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for getCandidateScore :- " + questionService.getCandidateScore("vdwoodie"));
		assertThat(questionService.examClosingUpdate("vdwoodie")).isEqualTo(2);
		assertThat(questionService.getCandidateScore("vdwoodie")).isEqualTo(2);
		questionService.setCandidateScore("vdwoodie", 15);
		assertThat(questionService.getCandidateScore("vdwoodie")).isEqualTo(15);
		assertThat(questionService.updateTestExam("vdwoodie").getUsername()).isEqualTo("vdwoodie");
		//logger.info("testUtilityFunctionsPass() invoked: for getCandidateScore After Update :- " + questionService.getCandidateScore("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for updateTestExam :- " + questionService.updateTestExam("vdwoodie").getUsername());
	}

	@Test
	public void testUtilityFunctionsFail() throws Exception {
		when(this.restDataRepository.getAnswersByUsername("testmathew")).thenReturn(testdata.generateMultipleChoice("testmathew"));
		when(this.restDataRepository.getQuestionsListByUserName("testmathew")).thenReturn(testdata.getMultipleQuestions("testmathew"));
		when(this.restDataRepository.getTestExamByUserName("testmathew")).thenReturn(new Testexam());
		questionService.LoadCandidateExam("testmathew");
		questionService.setQuestionNo("testmathew", 1);
		questionService.updateAnswers("testmathew", AnswerQuestion(1));
		assertThat(questionService.getQuestionNo("testmathew")).isEqualTo(0);
		questionService.setQuestionNo("testmathew", 10);
		questionService.updateAnswers("testmathew", AnswerQuestion(10));
		assertThat(questionService.getQuestionNo("testmathew")).isEqualTo(0);
		questionService.setQuestionNo("testmathew", 25);
		questionService.updateAnswers("testmathew", AnswerQuestion(25));
		//logger.info("testUtilityFunctionsPass() invoked: for getQuestionNo :- " + questionService.getQuestionNo("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for CountAllQuestions :- " + questionService.CountAllQuestions("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for getQuestion, Number :- " + questionService.getQuestion("vdwoodie").getDisplayorder());
		//logger.info("testUtilityFunctionsPass() invoked: for unAnswered (T/F) :- " + questionService.unAnsweredQuestions("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for getAnswers (Q1, A) :- " + questionService.getAnswers("vdwoodie", "Q1").get("B").getCandidateanswer());

		assertThat(questionService.getQuestionNo("testmathew")).isEqualTo(0);
		assertThat(questionService.CountAllQuestions("testmathew")).isEqualTo(0);
		assertThat(questionService.getQuestion("testmathew").getDisplayorder()).isNull();
		assertTrue(questionService.unAnsweredQuestions("testmathew"));
		assertTrue(questionService.getAnswers("testmathew", "Q1").isEmpty());
		//logger.info("testUtilityFunctionsPass() invoked: for examClosingUpdate :- " + questionService.examClosingUpdate("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for getCandidateScore :- " + questionService.getCandidateScore("vdwoodie"));
		assertThat(questionService.examClosingUpdate("testmathew")).isEqualTo(0);
		assertThat(questionService.getCandidateScore("testmathew")).isEqualTo(0);
		questionService.setCandidateScore("testmathew", 15);
		assertThat(questionService.getCandidateScore("testmathew")).isEqualTo(0);
		assertThat(questionService.updateTestExam("testmathew").getUsername()).isNull();
		//logger.info("testUtilityFunctionsPass() invoked: for getCandidateScore After Update :- " + questionService.getCandidateScore("vdwoodie"));
		//logger.info("testUtilityFunctionsPass() invoked: for updateTestExam :- " + questionService.updateTestExam("vdwoodie").getUsername());
	}
	
	private Map<String, Boolean> AnswerQuestion(int qNo){
		Map<String, Boolean> anschoice = new HashMap<>();
		anschoice.put("A", false);
		anschoice.put("B", false);
		anschoice.put("C", false);
		anschoice.put("D", false);
		if (qNo == 1){
			anschoice.put("A", true);
			anschoice.put("C", true);
		}
		if (qNo == 25){
			anschoice.put("A", true);
		}	
		if (qNo == 10){
			anschoice.put("A", true);
		}
		return anschoice;
	}
}
