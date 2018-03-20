package com.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;

import com.domain.Examparameters;
import com.domain.Multiplechoiceanswers;
import com.domain.Questions;
import com.domain.Testexam;
import com.domain.User;
import com.repository.MultipleChoiceAnswersRepository;
import com.repository.TestExamRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OnlineTestExamMakerWs2ControllerTest {
	protected Logger logger = Logger.getLogger(OnlineTestExamMakerWs2ControllerTest.class.getName());
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TestExamRepository testexamRepository;

	@Autowired
	private MultipleChoiceAnswersRepository multipleChoiceAnswersRepository;

	@Test
	public void testUserExist() throws Exception {
		User user = restTemplate.getForObject("http://localhost:" + port + "/user/{userName}", User.class, "vdwoodie");
		assertThat(user.getUsername()).isEqualTo("vdwoodie");
		assertThat(user.getFullname()).contains("Victor Woodrow");
	}

	@Test
	public void testUserNotExist() throws Exception {
		User user = restTemplate.getForObject("http://localhost:" + port + "/user/{userName}", User.class,
				"testmathew");
		assertThat(user.getId()).isEqualTo(0);
	}

	//
	@Test
	public void testExamExist() throws Exception {
		Testexam testexam = restTemplate.getForObject("http://localhost:" + port + "/Testexam/{userName}",
				Testexam.class, "vdwoodie");
		assertThat(testexam.getUsername()).isEqualTo("vdwoodie");
		assertThat(testexam.getExamcode()).contains("C0001");
	}

	@Test
	public void testExamNotExist() throws Exception {
		Testexam testexam = restTemplate.getForObject("http://localhost:" + port + "/Testexam/{userName}",
				Testexam.class, "testmathew");
		assertNull(testexam);
	}

	@Test
	public void testUpdateTestExam() throws Exception {
		Testexam testexam = testexamRepository.findByUsername("vdwoodie");
		testexam.setCorrectanswer(testexam.getCorrectanswer() + 1);
		testexam.setStartdatetime(new Date());
		testexam.setDurationmillsec(ConvertHoursMinutes(1,10));
		Testexam returns = restTemplate.postForObject("http://localhost:" + port + "/Testexam/save", testexam,Testexam.class, "vdwoodie");
		Long startT = returns.getStartdatetime().getTime();
		Long duraT = returns.getDurationmillsec().longValue();
		Long now = new Date().getTime();
		Long timeremain = (startT+duraT) - now;
        double days = Math.floor(timeremain / (1000 * 60 * 60 * 24));
        double hours = Math.floor((timeremain % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        double minutes = Math.floor((timeremain % (1000 * 60 * 60)) / (1000 * 60));
        double seconds = Math.floor((timeremain % (1000 * 60)) / 1000);
		String remaining = twoDigitString(hours)+":"+twoDigitString(minutes)+":"+twoDigitString(seconds);
				//String.format("%1$tH:%1$tM:%1$tS", timeremain);
		logger.info("testUpdateTestExam() invoked: for Time remaining :- " + remaining);
		assertThat(returns.getUsername()).isEqualTo("vdwoodie");
		assertThat(returns.getExamcode()).contains("C0001");
	}

	//
	@Test
	public void testQuestionExist() throws Exception {
		ResponseEntity<Questions[]> questions = restTemplate
				.getForEntity("http://localhost:" + port + "/Questions/{userName}", Questions[].class, "vdwoodie");
		Questions[] entitybody = questions.getBody();
		assertThat(entitybody[1].getUsername()).isEqualTo("vdwoodie");
	}

	@Test
	public void testQuestionNotExist() {
		Questions[] questions = {};
		try {
			questions = restTemplate.getForObject("http://localhost:" + port + "/Questions/{userName}",
					Questions[].class, "testmathew");
			assertThat(questions.length).isEqualTo(0);
		} catch (final HttpMessageNotReadableException e) {
			assertThat(questions.length).isEqualTo(0);
		} 	
	}

	//
	@Test
	public void testExamCodeExist() throws Exception {
		Examparameters examparameters = restTemplate.getForObject("http://localhost:" + port + "/parameter/{examCode}",
				Examparameters.class, "C0001");
		assertThat(examparameters.getExamcode()).isEqualTo("C0001");
		assertThat(examparameters.getExamname()).contains("Test Exam Maker");
	}

	@Test
	public void testExamCodeNotExist() throws Exception {
		Examparameters examparameters = restTemplate.getForObject("http://localhost:" + port + "/parameter/{examCode}",
				Examparameters.class, "C0002");
		assertThat(examparameters.getId()).isEqualTo(0);
	}

	//
	@Test
	public void testAnswersExist() throws Exception {
		Multiplechoiceanswers[] multiplechoiceanswers = restTemplate.getForObject(
				"http://localhost:" + port + "/multiplechoice/{userName}", Multiplechoiceanswers[].class, "vdwoodie");
		assertThat(multiplechoiceanswers[1].getUsername()).isEqualTo("vdwoodie");
	}

	@Test
	public void testAnswersNotExist() throws Exception {
		Multiplechoiceanswers[] multiplechoiceanswers = {};
		try {
			multiplechoiceanswers = restTemplate.getForObject("http://localhost:" + port + "/multiplechoice/{userName}",
					Multiplechoiceanswers[].class, "testmathew");
			assertThat(multiplechoiceanswers.length).isEqualTo(0);
		} catch (final HttpMessageNotReadableException e) {
			assertThat(multiplechoiceanswers.length).isEqualTo(0);
		}
	}

	//
	@Test
	public void testUpdateAnswers() throws Exception {
		List<Multiplechoiceanswers> MchoiceList = multipleChoiceAnswersRepository.findByUsernameAndTitle("vdwoodie","Q4");
		Multiplechoiceanswers mchoice = MchoiceList.get(0);
		mchoice.setCandidateanswer(!mchoice.getCandidateanswer());
		Multiplechoiceanswers returns = restTemplate.postForObject("http://localhost:" + port + "/multiplechoice/save",
				mchoice, Multiplechoiceanswers.class, "vdwoodie","Q4");
		assertThat(returns.getTitle()).isEqualTo("Q4");
		assertThat(returns.getUsername()).isEqualTo("vdwoodie");
	}
	
	public Integer ConvertHoursMinutes(Integer hours, Integer Minutes) {
		Integer duration = 0;
		if (hours > 0) {
			duration = duration+(60 * 60 * hours);
		}
		if (Minutes > 0) {
			duration = duration+(60 * Minutes);
		}
		return duration * 1000;
	}
	public String twoDigitString(Double timing){
		Integer memint = timing.intValue();
		String varmem = memint.toString().trim();
		if (varmem.length() == 1){
			varmem = "0"+varmem;
		}	
		return varmem;
	}
}
