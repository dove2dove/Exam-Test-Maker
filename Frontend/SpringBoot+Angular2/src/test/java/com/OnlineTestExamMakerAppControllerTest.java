package com;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.controller.ExamMakerController;
import com.domain.Testexam;
//import com.domain.Login;
import com.domain.User;
import com.service.QuestionService;


@RunWith(SpringRunner.class)
@WebMvcTest(ExamMakerController.class)
public class OnlineTestExamMakerAppControllerTest {
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

	TestData testdata;

	@Before
	public void setUp(){
		testdata = new TestData();
	}
    //
    @Test
    public void testAuthenticateUserPass() throws Exception {
        //
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, String> login = new HashMap<>();
    	login.put("userName", "vdwoodie");
    	login.put("userPassword", "123456");
        when(questionService.getUserByUserName("vdwoodie")).thenReturn(new User(1, "vdwoodie", "Victor Woodrow", "123456"));
        when(questionService.LoadCandidateExam("vdwoodie")).thenReturn(true);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(login))).andReturn();
        //MvcResult result = this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).param("userName","vdwoodie").param("userPassword","123456")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Login Successful")));
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
    }
    
    @Test
    public void testAuthenticateUserWrongPassword() throws Exception {
        //
    	Map<String, String> login = new HashMap<>();
    	login.put("userName", "vdwoodie");
    	login.put("userPassword", "654321");/////
        when(questionService.getUserByUserName("vdwoodie")).thenReturn(new User(1, "vdwoodie", "Victor Woodrow", "123456"));
        when(questionService.LoadCandidateExam("vdwoodie")).thenReturn(true);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(login))).andReturn();
        //MvcResult result = this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).param("userName","vdwoodie").param("userPassword","123456")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Login Successful")));
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.NOT_FOUND.value(), status);
    }
    
    @Test
    public void testAuthenticateUserNotLoaded() throws Exception {
        //
    	Map<String, String> login = new HashMap<>();
    	login.put("userName", "vdwoodie");
    	login.put("userPassword", "123456");
        when(questionService.getUserByUserName("vdwoodie")).thenReturn(new User(1, "vdwoodie", "Victor Woodrow", "123456"));
        when(questionService.LoadCandidateExam("vdwoodie")).thenReturn(false);////
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(login))).andReturn();
        //MvcResult result = this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).param("userName","vdwoodie").param("userPassword","123456")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Login Successful")));
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.UNAUTHORIZED.value(), status);
    }
    
    @Test
    public void testAuthenticateUserWrongData() throws Exception {
        //
    	Map<String, String> login = new HashMap<>();
    	login.put("userName", null);
    	login.put("userPassword", "123456");
        when(questionService.getUserByUserName("vdwoodie")).thenReturn(new User(1, "vdwoodie", "Victor Woodrow", "123456"));
        when(questionService.LoadCandidateExam("vdwoodie")).thenReturn(false);////
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(login))).andReturn();
        //MvcResult result = this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).param("userName","vdwoodie").param("userPassword","123456")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Login Successful")));
		// verify
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.BAD_REQUEST.value(), status);
    }
    @Test
    public void testgetNextQuestionPositive() throws Exception {
        when(questionService.updateAnswers("vdwoodie", new HashMap<String, Boolean>())).thenReturn(true);
        when(questionService.getQuestionNo("vdwoodie")).thenReturn(1);
        when(questionService.setQuestionNo("vdwoodie", 1)).thenReturn(true);
        when(questionService.CountAllQuestions("vdwoodie")).thenReturn(25);
        when(questionService.getQuestion("vdwoodie")).thenReturn(testdata.getSingleQuestion("vdwoodie", 1));
        when(questionService.examClosingUpdate("vdwoodie")).thenReturn(2);
        when(questionService.updateTestExam("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
        when(questionService.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "vdwoodie");
    	formdata.put("Answers", userAnswer);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/next").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
    } 
    @Test
    public void testgetNextQuestionNoDataNegative() throws Exception {
        when(questionService.updateAnswers("vdwoodie", new HashMap<String, Boolean>())).thenReturn(true);
        when(questionService.getQuestionNo("vdwoodie")).thenReturn(1);
        when(questionService.setQuestionNo("vdwoodie", 1)).thenReturn(true);
        when(questionService.CountAllQuestions("vdwoodie")).thenReturn(25);
        when(questionService.getQuestion("vdwoodie")).thenReturn(testdata.getSingleQuestion("vdwoodie", 1));
        when(questionService.examClosingUpdate("vdwoodie")).thenReturn(2);
        when(questionService.updateTestExam("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
        when(questionService.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "vdwoodie");
    	formdata.put("Answers", userAnswer);
    	formdata = new HashMap<>();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/next").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.BAD_REQUEST.value(), status);
    } 
    @Test
    public void testgetNextQuestionNegative() throws Exception {
        when(questionService.updateAnswers("testMathew", new HashMap<String, Boolean>())).thenReturn(false);
        when(questionService.getQuestionNo("testMathew")).thenReturn(0);
        when(questionService.setQuestionNo("testMathew", 1)).thenReturn(false);
        when(questionService.CountAllQuestions("testMathew")).thenReturn(0);
        when(questionService.getQuestion("testMathew")).thenReturn(testdata.getSingleQuestion("testMathew", 1));
        when(questionService.examClosingUpdate("testMathew")).thenReturn(0);
        when(questionService.updateTestExam("testMathew")).thenReturn(new Testexam());
        when(questionService.getTestExamByUserName("testMathew")).thenReturn(new Testexam());
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "testMathew");
    	formdata.put("Answers", userAnswer);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/next").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.BAD_REQUEST.value(), status);
    } 
    @Test
    public void testgetPreviousQuestionPositive() throws Exception {
        when(questionService.updateAnswers("vdwoodie", new HashMap<String, Boolean>())).thenReturn(true);
        when(questionService.getQuestionNo("vdwoodie")).thenReturn(1);
        when(questionService.setQuestionNo("vdwoodie", 1)).thenReturn(true);
        when(questionService.CountAllQuestions("vdwoodie")).thenReturn(25);
        when(questionService.getQuestion("vdwoodie")).thenReturn(testdata.getSingleQuestion("vdwoodie", 1));
        when(questionService.examClosingUpdate("vdwoodie")).thenReturn(2);
        when(questionService.updateTestExam("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
        when(questionService.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "vdwoodie");
    	formdata.put("Answers", userAnswer);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/previous").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
    } 
    @Test
    public void testgetPreviousQuestionNoDataNegative() throws Exception {
        when(questionService.updateAnswers("vdwoodie", new HashMap<String, Boolean>())).thenReturn(true);
        when(questionService.getQuestionNo("vdwoodie")).thenReturn(1);
        when(questionService.setQuestionNo("vdwoodie", 1)).thenReturn(true);
        when(questionService.CountAllQuestions("vdwoodie")).thenReturn(25);
        when(questionService.getQuestion("vdwoodie")).thenReturn(testdata.getSingleQuestion("vdwoodie", 1));
        when(questionService.examClosingUpdate("vdwoodie")).thenReturn(2);
        when(questionService.updateTestExam("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
        when(questionService.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "vdwoodie");
    	formdata.put("Answers", userAnswer);
    	formdata = new HashMap<>();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/previous").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.BAD_REQUEST.value(), status);
    }
    @Test
    public void testgetPreviousQuestionNegative() throws Exception {
        when(questionService.updateAnswers("testMathew", new HashMap<String, Boolean>())).thenReturn(false);
        when(questionService.getQuestionNo("testMathew")).thenReturn(0);
        when(questionService.setQuestionNo("testMathew", 1)).thenReturn(false);
        when(questionService.CountAllQuestions("testMathew")).thenReturn(0);
        when(questionService.getQuestion("testMathew")).thenReturn(testdata.getSingleQuestion("testMathew", 1));
        when(questionService.examClosingUpdate("testMathew")).thenReturn(0);
        when(questionService.updateTestExam("testMathew")).thenReturn(new Testexam());
        when(questionService.getTestExamByUserName("testMathew")).thenReturn(new Testexam());
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "testMathew");
    	formdata.put("Answers", userAnswer);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/previous").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.BAD_REQUEST.value(), status);
    }
    @Test
    public void testgetQuestionbyNumberPositive() throws Exception {
        when(questionService.updateAnswers("vdwoodie", new HashMap<String, Boolean>())).thenReturn(true);
        when(questionService.getQuestionNo("vdwoodie")).thenReturn(1);
        when(questionService.setQuestionNo("vdwoodie", 1)).thenReturn(true);
        when(questionService.CountAllQuestions("vdwoodie")).thenReturn(25);
        when(questionService.getQuestion("vdwoodie")).thenReturn(testdata.getSingleQuestion("vdwoodie", 1));
        when(questionService.examClosingUpdate("vdwoodie")).thenReturn(2);
        when(questionService.updateTestExam("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
        when(questionService.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "vdwoodie");
    	formdata.put("questionNo", "10"); 	
    	formdata.put("Answers", userAnswer);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/questionNo").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
    } 
    @Test
    public void testgetQuestionbyNumberNoDataNegative() throws Exception {
        when(questionService.updateAnswers("vdwoodie", new HashMap<String, Boolean>())).thenReturn(true);
        when(questionService.getQuestionNo("vdwoodie")).thenReturn(1);
        when(questionService.setQuestionNo("vdwoodie", 1)).thenReturn(true);
        when(questionService.CountAllQuestions("vdwoodie")).thenReturn(25);
        when(questionService.getQuestion("vdwoodie")).thenReturn(testdata.getSingleQuestion("vdwoodie", 1));
        when(questionService.examClosingUpdate("vdwoodie")).thenReturn(2);
        when(questionService.updateTestExam("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
        when(questionService.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "vdwoodie");
    	formdata.put("questionNo", "10"); 	
    	formdata.put("Answers", userAnswer);
    	formdata = new HashMap<>();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/questionNo").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.BAD_REQUEST.value(), status);
    } 
    @Test
    public void testgetQuestionbyNumberNegative() throws Exception {
        when(questionService.updateAnswers("testMathew", new HashMap<String, Boolean>())).thenReturn(false);
        when(questionService.getQuestionNo("testMathew")).thenReturn(0);
        when(questionService.setQuestionNo("testMathew", 1)).thenReturn(false);
        when(questionService.CountAllQuestions("testMathew")).thenReturn(0);
        when(questionService.getQuestion("testMathew")).thenReturn(testdata.getSingleQuestion("testMathew", 1));
        when(questionService.examClosingUpdate("testMathew")).thenReturn(0);
        when(questionService.updateTestExam("testMathew")).thenReturn(new Testexam());
        when(questionService.getTestExamByUserName("testMathew")).thenReturn(new Testexam());
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "testMathew");
    	formdata.put("questionNo", "10"); 
    	formdata.put("Answers", userAnswer);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/questionNo").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.BAD_REQUEST.value(), status);
    }
    @Test
    public void testgetEndTestExamPositive() throws Exception {
        when(questionService.updateAnswers("vdwoodie", new HashMap<String, Boolean>())).thenReturn(true);
        when(questionService.getQuestionNo("vdwoodie")).thenReturn(1);
        when(questionService.setQuestionNo("vdwoodie", 1)).thenReturn(true);
        when(questionService.CountAllQuestions("vdwoodie")).thenReturn(25);
        when(questionService.getQuestion("vdwoodie")).thenReturn(testdata.getSingleQuestion("vdwoodie", 1));
        when(questionService.examClosingUpdate("vdwoodie")).thenReturn(2);
        when(questionService.updateTestExam("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
        when(questionService.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "vdwoodie");
    	formdata.put("Answers", userAnswer);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/endexam").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.OK.value(), status);
    } 
    @Test
    public void testgetEndTestExamNoDataNegative() throws Exception {
        when(questionService.updateAnswers("vdwoodie", new HashMap<String, Boolean>())).thenReturn(true);
        when(questionService.getQuestionNo("vdwoodie")).thenReturn(1);
        when(questionService.setQuestionNo("vdwoodie", 1)).thenReturn(true);
        when(questionService.CountAllQuestions("vdwoodie")).thenReturn(25);
        when(questionService.getQuestion("vdwoodie")).thenReturn(testdata.getSingleQuestion("vdwoodie", 1));
        when(questionService.examClosingUpdate("vdwoodie")).thenReturn(2);
        when(questionService.updateTestExam("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
        when(questionService.getTestExamByUserName("vdwoodie")).thenReturn(new Testexam(1, "C0001","vdwoodie"));
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "vdwoodie");
    	formdata.put("Answers", userAnswer);
    	formdata = new HashMap<>();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/endexam").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.BAD_REQUEST.value(), status);
    } 
    @Test
    public void testgetEndTestExamNegative() throws Exception {
        when(questionService.updateAnswers("testMathew", new HashMap<String, Boolean>())).thenReturn(false);
        when(questionService.getQuestionNo("testMathew")).thenReturn(0);
        when(questionService.setQuestionNo("testMathew", 1)).thenReturn(false);
        when(questionService.CountAllQuestions("testMathew")).thenReturn(0);
        when(questionService.getQuestion("testMathew")).thenReturn(testdata.getSingleQuestion("testMathew", 1));
        when(questionService.examClosingUpdate("testMathew")).thenReturn(0);
        when(questionService.updateTestExam("testMathew")).thenReturn(new Testexam());
        when(questionService.getTestExamByUserName("testMathew")).thenReturn(new Testexam());
    	//Login login = new Login("vdwoodie", "123456");
    	Map<String, Object> formdata = new HashMap<>();
    	Map<String, Boolean> userAnswer = new HashMap<>();
    	userAnswer.put("A", false);
    	userAnswer.put("B", true);
    	userAnswer.put("C", true);
    	userAnswer.put("D", false);
    	formdata.put("userName", "testMathew");
    	formdata.put("Answers", userAnswer);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/question/endexam").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(formdata))).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals("Correct Response Status", HttpStatus.BAD_REQUEST.value(), status);
    }
}
