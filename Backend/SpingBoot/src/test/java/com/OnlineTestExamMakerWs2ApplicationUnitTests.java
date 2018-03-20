package com;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.domain.Examparameters;
import com.domain.Multiplechoiceanswers;
import com.domain.Questions;
import com.domain.Testexam;
import com.domain.User;
import com.repository.ExamparametersRepository;
import com.repository.MultipleChoiceAnswersRepository;
import com.repository.QuestionsRepository;
import com.repository.TestExamRepository;
import com.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineTestExamMakerWs2ApplicationUnitTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TestExamRepository testexamRepository;
	@Autowired
	private QuestionsRepository questionsRepository;
	@Autowired
	private MultipleChoiceAnswersRepository multipleChoiceAnswersRepository;
	@Autowired
	private ExamparametersRepository examparametersRepository;

	    @Test
	    public void contexLoads() throws Exception {
	        assertThat(userRepository).isNotNull();
	        assertThat(testexamRepository).isNotNull();
	        assertThat(questionsRepository).isNotNull();
	        assertThat(multipleChoiceAnswersRepository).isNotNull();
	        assertThat(examparametersRepository).isNotNull();
	    }

	@Test
	public void testUserExistRepository() throws Exception {
		User user = this.userRepository.findByUsername("vdwoodie");
		assertThat(user.getUsername()).isEqualTo("vdwoodie");
		assertThat(user.getFullname()).isEqualTo("Victor Woodrow");
	}

	@Test
	public void testUserNotExistRepository() throws Exception {
		User user = this.userRepository.findByUsername("testmathew");
		assertNull(user);
	}

	@Test
	public void testExamExistRepository() throws Exception {
		Testexam testexam = this.testexamRepository.findByUsername("vdwoodie");
		assertThat(testexam.getUsername()).isEqualTo("vdwoodie");
		assertThat(testexam.getExamcode()).isEqualTo("C0001");
	}

	@Test
	public void testExamNotExistRepository() throws Exception {
		Testexam testexam = this.testexamRepository.findByUsername("testmathew");
		assertNull(testexam);
	}

	//
	@Test
	public void testSingleQuestionExistRepository() throws Exception {
		Questions questions = this.questionsRepository.findByUsernameAndTitle("vdwoodie", "Q4");
		assertThat(questions.getUsername()).isEqualTo("vdwoodie");
		assertThat(questions.getTitle()).isEqualTo("Q4");
	}

	@Test
	public void testSingleQuestionNotExistRepository() throws Exception {
		Questions questions = this.questionsRepository.findByUsernameAndTitle("testmathew", "Q4");
		assertNull(questions);
	}

	//
	@Test
	public void testMultipleQuestionExistRepository() throws Exception {
		List<Questions> questions = this.questionsRepository.findByUsername("vdwoodie");
		assertThat(questions.get(1).getUsername()).isEqualTo("vdwoodie");
	}

	@Test
	public void testMultipleQuestionNotExistRepository() throws Exception {
		List<Questions> questions = this.questionsRepository.findByUsername("testmathew");
		assertThat(questions.size()).isEqualTo(0);
		//assertNull(questions);
	}

	//
	@Test
	public void testParameterExistRepository() throws Exception {
		Examparameters examparameters = this.examparametersRepository.findByExamcode("C0001");
		assertThat(examparameters.getExamcode()).isEqualTo("C0001");
		assertThat(examparameters.getExamdescription()).contains("Crossover");
	}

	@Test
	public void testParameterNotExistRepository() throws Exception {
		Examparameters examparameters = this.examparametersRepository.findByExamcode("C0002");
		assertNull(examparameters);
	}

	//
	@Test
	public void testAnswerExistRepository() throws Exception {
		List<Multiplechoiceanswers> choiceanswer = this.multipleChoiceAnswersRepository.findByUsername("vdwoodie");
		assertThat(choiceanswer.get(1).getUsername()).isEqualTo("vdwoodie");
	}

	@Test
	public void testAnswerNotExistRepository() throws Exception {
		List<Multiplechoiceanswers> choiceanswer = this.multipleChoiceAnswersRepository.findByUsername("testmathew");
		assertThat(choiceanswer.size()).isEqualTo(0);
	}
}
