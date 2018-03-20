package com.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.domain.Examparameters;
import com.domain.Multiplechoiceanswers;
import com.domain.Questions;
import com.domain.Testexam;
import com.domain.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.service.QuestionService;

/**
 * {@link QuestionService}.
 * 
 * @author Victor Woodrow
 */
@Controller
public class ExamMakerController {
	protected QuestionService questionService;
	protected Logger logger = Logger.getLogger(ExamMakerController.class.getName());
	protected String[] mapha = {"A", "B", "C", "D", "Y", "Z"};
//
	@Autowired
	public ExamMakerController(QuestionService questionService) {
		this.questionService = questionService;
	}
//
	@RequestMapping("/parameter/C0001")
	public ResponseEntity<Map<String, Object>> home() {
		Examparameters examparameter = questionService.getExamParameterByExamCode("C0001");
		Map<String,Object> model = new HashMap<String,Object>();
		if (examparameter != null) {
			model.put("id", UUID.randomUUID().toString());
			model.put("content", examparameter.getExamdescription());
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		} else {
			model.put("id", UUID.randomUUID().toString());
			model.put("content", " ");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.BAD_REQUEST);
		}
	}
//
	@RequestMapping(value = "/timer", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getTimeRemain(@RequestBody final Map<String, String> UserNameMap) {
		Map<String,Object> model = new HashMap<String,Object>();
		if ((UserNameMap.get("userName") != null) && (!UserNameMap.get("userName").trim().isEmpty())) {
			String timerem = questionService.getTimeRemain(UserNameMap.get("userName").trim());
			model.put("TimeRemain", timerem);
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
		} else {
			model.put("TimeRemain", "");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.BAD_REQUEST);
		}
	}
	//
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> authenticateUser(@RequestBody final Map<String, String> LoginUser) {
		logger.info("ExamMakerController: authenticateUser() invoked ");
		Map<String, Object> model = new HashMap<>();
		logger.info("ExamMakerController: authenticateUser() invoked userName:-"+LoginUser.get("userName")+", userPassword:-"+LoginUser.get("userPassword"));
		if ((LoginUser.get("userName") != null) && (!LoginUser.get("userName").trim().isEmpty())) {
			User user = questionService.getUserByUserName(LoginUser.get("userName").trim());
			if (LoginUser.get("userName").trim().equals(user.getUsername().trim())
					&& LoginUser.get("userPassword").trim().equals(user.getUserpassword().trim())) {
				// Load questions into service
				boolean loadsuccess = questionService.LoadCandidateExam(user.getUsername().trim());
				if (loadsuccess) {
					Testexam testexam = questionService.updateStartTime(user.getUsername().trim());
					if (testexam.getId() == null){
						logger.info("ExamMakerController: authenticateUser() invoked : Exam StartTime and Duration was not updated");
					}
					logger.info("ExamMakerController: authenticateUser() invoked : Successful");
					model.put("userName", user.getUsername().trim());
					model.put("TotalQuestion", questionService.CountAllQuestions(user.getUsername().trim()));
					model.put("QuestionNo", "1");
					model.put("TimeRemain", questionService.getTimeRemain(user.getUsername().trim()));				
					model.put("Message", "Login Successful");
					return new ResponseEntity<Map<String, Object>>(model, HttpStatus.OK);
				} else {
					logger.info("ExamMakerController: authenticateUser() invoked : User not Authorized");
					model.put("userName", null);
					model.put("TotalQuestion", null);
					model.put("QuestionNo", "0");
					model.put("TimeRemain", "");	
					model.put("Message", "User not Authorized");
					return new ResponseEntity<Map<String, Object>>(model, HttpStatus.UNAUTHORIZED);
				}
			} else {
				model.put("userName", null);
				model.put("TotalQuestion", null);
				model.put("QuestionNo", "0");
				model.put("TimeRemain", "");
				model.put("Message", "UserName or Password Not Found");
				logger.info("ExamMakerController: authenticateUser() invoked : UserName or Password Not Found");
				return new ResponseEntity<Map<String, Object>>(model, HttpStatus.NOT_FOUND);
			}
		} else {
			model.put("userName", null);
			model.put("TotalQuestion", null);
			model.put("QuestionNo", "0");
			model.put("TimeRemain", "");
			model.put("Message", "Data issue with Request");
			logger.info("ExamMakerController: authenticateUser() invoked : Data issue with Request");
			return new ResponseEntity<Map<String, Object>>(model, HttpStatus.BAD_REQUEST);
		}
	}

	//
	@SuppressWarnings("unchecked")
	// @CrossOrigin(origins = "http://localhost:8090")
	@RequestMapping(value = "/question/next", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getNextQuestion(@RequestBody final Map<String, Object> userAnswers) {
		logger.info("ExamMakerController: getNextQuestion() invoked ");
		String userName = "";
		if ((userAnswers != null) && (!userAnswers.isEmpty())) {
			userName = (String) userAnswers.get("userName");
			Map<String, Boolean> userAnswerMap = (Map<String, Boolean>) userAnswers.get("Answers");
			questionService.updateAnswers(userName, userAnswerMap);
			if (questionService.CountAllQuestions(userName) > questionService.getQuestionNo(userName)) {
				questionService.setQuestionNo(userName, questionService.getQuestionNo(userName) + 1);
			}
		}
		return getQuestionObjects(userName);
	}

	//
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/question/previous", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getPreviousQuestion(@RequestBody final Map<String, Object> userAnswers) {
		logger.info("ExamMakerController: getPreviousQuestion() invoked ");
		String userName = "";
		if ((userAnswers != null) && (!userAnswers.isEmpty())) {
			userName = (String) userAnswers.get("userName");
			Map<String, Boolean> userAnswerMap = (Map<String, Boolean>) userAnswers.get("Answers");
			questionService.updateAnswers(userName, userAnswerMap);
			if (questionService.getQuestionNo(userName) > 1) {
				questionService.setQuestionNo(userName, questionService.getQuestionNo(userName) - 1);
			}
		}
		return getQuestionObjects(userName);
	}

	//
	//@SuppressWarnings("unchecked")
	@RequestMapping(value = "/question/questionNo", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Map<String, Object>> getQuestionbyNumber(@RequestBody final String userAnswers) {
		logger.info("ExamMakerController: getQuestionbyNumber() invoked ");
		// Integer questNo;
		Map<String, Object> questionAnswer = new HashMap<>();
		if ((userAnswers != null) && (!userAnswers.isEmpty())) {
			JsonElement jelement = new JsonParser().parse(userAnswers);
			JsonObject jobject = jelement.getAsJsonObject();
			String userName = jobject.get("userName").getAsString();
			Integer questionNo = jobject.get("questionNo").getAsInt();
			Integer preQuestionNo = jobject.get("preQuestionNo").getAsInt();
			JsonArray Answersarray = jobject.getAsJsonArray("Answers");
			logger.info("ExamMakerController: getQuestionbyNumber() invoked :- JSON answer format " + userAnswers);
			Map<String, Boolean> userAnswerMap = converttoMap(Answersarray);
			logger.info("ExamMakerController: getQuestionbyNumber() invoked :- Data sent from client is clean : " + userName + ", " + questionNo);
			if ((userAnswerMap.size() > 1) && (preQuestionNo >= 0)) {
				logger.info("ExamMakerController: getQuestionbyNumber() invoked :- Size of Sent Answers is greater than 1, should call answer update");
				questionService.setQuestionNo(userName, preQuestionNo);
				questionService.updateAnswers(userName, userAnswerMap);
			}else{
				logger.info("ExamMakerController: getQuestionbyNumber() invoked :- Size of Sent Answers is Less than or equal 1, cannot call answerupdate");
			}
			logger.info("ExamMakerController: getQuestionbyNumber() invoked :- QuestionNo and UserName " + questionNo+", "+userName);
			if ((questionNo >= 0) & ((userName != null) && (!userName.isEmpty()))) {
				int qestcnt = questionService.CountAllQuestions(userName);
				if ((questionNo >= 1) & (questionNo <= qestcnt)) {
					logger.info("ExamMakerController: getQuestionbyNumber() invoked :- setting question number sent from client :- "+ questionNo);
					questionService.setQuestionNo(userName, questionNo);
					return getQuestionObjects(userName);
				} else {
					logger.info("ExamMakerController: getQuestionbyNumber() invoked :- issues with questionNo or Username sent from client");
					return new ResponseEntity<Map<String, Object>>(questionAnswer, HttpStatus.BAD_REQUEST);
				}
			} else {
				logger.info("ExamMakerController: getQuestionbyNumber() invoked :- QuestionNo or UserName sent from client is null or empty");
				return new ResponseEntity<Map<String, Object>>(questionAnswer, HttpStatus.BAD_REQUEST);
			}
		} else {
			logger.info("ExamMakerController: getQuestionbyNumber() invoked :- Request Data is null or empty");
			return new ResponseEntity<Map<String, Object>>(questionAnswer, HttpStatus.BAD_REQUEST);
		}
	}

	//
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/question/endexam", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getEndTestExam(@RequestBody final Map<String, Object> userAnswers) {
		logger.info("ExamMakerController: getEndTestExam() invoked ");
		Map<String, Object> questionAnswer = new HashMap<>();
		String userName = "";
		if ((userAnswers != null) && (!userAnswers.isEmpty())) {
			userName = (String) userAnswers.get("userName");
			Map<String, Boolean> userAnswerMap = (Map<String, Boolean>) userAnswers.get("Answers");
			questionService.updateAnswers(userName, userAnswerMap);
			questionService.examClosingUpdate(userName);
			questionService.updateTestExam(userName);
			Testexam result = questionService.getTestExamByUserName(userName);
			if ((result != null) && (result.getId() != null)) {
				questionAnswer.put("correctanswers", result.getCorrectanswer());
				questionAnswer.put("totalquestions", result.getTotalquestions());
				questionAnswer.put("totalscore", result.getTotalscore());
				return new ResponseEntity<Map<String, Object>>(questionAnswer, HttpStatus.OK);
			} else {
				questionAnswer.put("correctanswers", null);
				questionAnswer.put("totalquestions", null);
				questionAnswer.put("totalscore", null);
				return new ResponseEntity<Map<String, Object>>(questionAnswer, HttpStatus.BAD_REQUEST);
			}
		} else {
			questionAnswer.put("correctanswers", null);
			questionAnswer.put("totalquestions", null);
			questionAnswer.put("totalscore", null);
			return new ResponseEntity<Map<String, Object>>(questionAnswer, HttpStatus.BAD_REQUEST);
		}
	}

	//
	private ResponseEntity<Map<String, Object>> getQuestionObjects(String userName) {
		Questions examquest = questionService.getQuestion(userName);
		Map<String, Object> questionAnswer = new HashMap<>();
		if ((examquest != null) && (examquest.getId() != null)){
			logger.info("ExamMakerController: getQuestionObjects() invoked :- Requested Question successfully found");
			questionAnswer.put("QuestionDetails", examquest.getProblemdescription());
			questionAnswer.put("QuestionNo", examquest.getDisplayorder());
			questionAnswer.put("multipleNotSingle", examquest.getMorethanoneanswer());
			logger.info("ExamMakerController: getQuestionObjects() invoked :- Getting Answers with userName and Title :- "+userName+", "+examquest.getTitle());
			Map<String, Multiplechoiceanswers> qanswers = questionService.getAnswers(userName, examquest.getTitle());
			qanswers.entrySet().forEach(entry -> {
				logger.info("Key : " + entry.getKey() + " Value : " + entry.getValue().getAnswerchoice());
			}); 
			questionAnswer.put("Answers", qanswers);
			return new ResponseEntity<Map<String, Object>>(questionAnswer, HttpStatus.OK);
		} else {
			logger.info("ExamMakerController: getQuestionObjects() invoked :- Requested Question Not found");
			questionAnswer.put("QuestionDetails", null);
			questionAnswer.put("QuestionNo", null);
			questionAnswer.put("multipleNotSingle", null);
			questionAnswer.put("Answers", null);
			return new ResponseEntity<Map<String, Object>>(questionAnswer, HttpStatus.BAD_REQUEST);
		}
	}

	private Map<String, Boolean> converttoMap(JsonArray Answersarray) {
		Map<String, Boolean> respMap = new HashMap<>();
		JsonObject jobject = null;
		for (int k = 0; k < Answersarray.size(); k++) {
			jobject = Answersarray.get(k).getAsJsonObject();
			jobject.get("id").getAsString();
			jobject.get("isChecked").getAsBoolean();
			respMap.put(jobject.get("id").getAsString(), jobject.get("isChecked").getAsBoolean());
			logger.info("ExamMakerController: converttoMap() invoked :- Answer List received "
					+ jobject.get("id").getAsString() + ", " + jobject.get("isChecked").getAsBoolean());
		}
		return respMap;
	}
}
