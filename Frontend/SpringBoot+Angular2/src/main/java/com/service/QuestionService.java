package com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;

import com.domain.Examparameters;
import com.domain.Multiplechoiceanswers;
import com.domain.Questions;
import com.domain.Testexam;
import com.domain.User;
import com.exceptions.DisplayRecordNotFoundError;

/**
 * Hide the access to the microservice inside this local service.
 * 
 * @author Paul Chapman
 */
@Service
public class QuestionService {

	@Autowired
	RestDataRepository restDataRepository;
	protected Map<String, Integer> allcandidateScore;
	protected Map<String, Integer> allcandidateinfo;
	protected Map<String, Map<Integer, Questions>> allcandidateQuestions;
	protected Map<String, Map<String, List<Multiplechoiceanswers>>> allMultipleChoiceAnswers;

	protected Logger logger = Logger.getLogger(QuestionService.class.getName());

	public QuestionService() {
		// public QuestionService(String serviceUrl) {
		// this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl :
		// "http://" + serviceUrl;
		allcandidateinfo = new HashMap<>();
		allcandidateScore = new HashMap<>();
		allcandidateQuestions = new HashMap<>();
		allMultipleChoiceAnswers = new HashMap<>();
		//this.restDataRepository = new RestDataRepository(restTemplate);
	}
	public boolean LoadCandidateExam(String userName) {
		boolean loadsuccess = false;
		List<Questions> questionList = getQuestionsListByUserName(userName);
		if ((questionList == null) || (questionList.isEmpty())) {
			loadsuccess = false;
			logger.info("QuestionService: LoadCandidateExam() invoked :- No questions loaded for candidate, Questions Not Found "+userName);
		} else {
			TreeMap<Integer, Questions> questionmaps = new TreeMap<>();
			for (Questions questions : questionList) {
				questionmaps.put(questions.getDisplayorder(), questions);
			}
			allcandidateQuestions.put(userName, questionmaps);
			logger.info("QuestionService: LoadCandidateExam() invoked :- Total Questions loaded for candiate "+userName+" = "+questionmaps.size());
			allMultipleChoiceAnswers.put(userName, restDataRepository.getAnswersByUsername(userName));
			logger.info("QuestionService: LoadCandidateExam() invoked :- Total Answers loaded for candiate "+userName+" = "+restDataRepository.getAnswersByUsername(userName).size());
			allcandidateinfo.put(userName, 0);
			allcandidateScore.put(userName, 0);
			loadsuccess = true;
		}
		return loadsuccess;
	}

	public Examparameters getExamParameterByExamCode(String examCode) {
		logger.info("getExamParameterByExamCode() invoked: for " + examCode);
		Examparameters examparameters = restDataRepository.getExamParameterByExamCode(examCode);
		if ((examparameters == null) || (examparameters.getId() == null)) {
			logger.info("getExamParameterByExamCode: Web service  getExamparameters object return null ");
			new DisplayRecordNotFoundError("Examparameters", "examCode", examCode);
			examparameters = new Examparameters();
		} else {
			logger.info(
					"getExamParameterByExamCode: Web service  getExamparameters object return Examparameters object ");
		}
		return examparameters;
	}

	public User getUserByUserName(String userName) {
		logger.info("findUserByUserName() invoked: for " + userName);
		User user = restDataRepository.getUserByUserName(userName);
		if ((user == null) || (user.getId() == null)) {
			logger.info("getUserByUserName: Web service  getUser object return null ");
			new DisplayRecordNotFoundError("User", "Username", userName);
			user = new User();
		} else {
			logger.info("getUserByUserName: Web service  getUser object return User Object ");
		}
		return user;
	}

	public Testexam getTestExamByUserName(String userName) {
		//logger.info("getTestExamByUserName() invoked: for " + userName);
		Testexam textexam = restDataRepository.getTestExamByUserName(userName);
		if ((textexam == null) || (textexam.getId() == null)) {
			logger.info("getTestExamByUserName: Web service  getTestexam object return null ");
			new DisplayRecordNotFoundError("Testexam", "Username", userName);
			textexam = new Testexam();
		} else {
			//logger.info("getTestExamByUserName: Web service  getTestexam object return Testexam object ");
		}
		return textexam;
	}

	public List<Questions> getQuestionsListByUserName(String userName) {
		logger.info("getQuestionsListByUserName() invoked:  for " + userName);
		Questions[] questions = {};
		try {
			questions = restDataRepository.getQuestionsListByUserName(userName);
		} catch (final HttpMessageNotReadableException e) { // 404
			// Nothing found
		}
		if ((questions == null) || (questions.length == 0)) {
			logger.info("getQuestionsListByUserName: Web service  getQuestions object return null ");
			new DisplayRecordNotFoundError("Questions", "Username", userName);
			return new ArrayList<>();
		} else {
			logger.info("getQuestionsListByUserName: Web service  getQuestions object return Questions object ");
			return Arrays.asList(questions);
		}
	}

	public Questions getQuestionByUserNameAndTitle(String userName, String title) {
		logger.info("getQuestionByUserNameAndTitle() invoked:  for " + userName);
		Questions questions = restDataRepository.getQuestionByUserNameAndTitle(userName, title);
		if ((questions == null) || (questions.getId() == null)) {
			logger.info("getQuestionByUserNameAndTitle: Web service  getquestions object return null ");
			new DisplayRecordNotFoundError("Questions", "Username", userName);
			questions = new Questions();
		} else {
			logger.info("getQuestionByUserNameAndTitle: Web service  getquestions object return questions object ");
		}
		return questions;
	}

	public Map<String, List<Multiplechoiceanswers>> getAnswersByUsername(String userName) {
		logger.info("getAnswersByUsername() invoked:  for " + userName);
		Map<String, List<Multiplechoiceanswers>> multiplechoiceanswers = new HashMap<>();
		try {
			multiplechoiceanswers = restDataRepository.getAnswersByUsername(userName);
		} catch (final HttpMessageNotReadableException e) { // 404
			// Nothing found
		}
		if ((multiplechoiceanswers == null) || (multiplechoiceanswers.isEmpty())) {
			logger.info("getAnswersByUsername: Web service  getQuestions object return null ");
			new DisplayRecordNotFoundError("Multiplechoiceanswers", "Username", userName);
			multiplechoiceanswers = new HashMap<>();
		} else {
			logger.info(
					"getAnswersByUsername: Web service  getMultiplechoiceanswers object return Multiplechoiceanswers object ");
		}
		return multiplechoiceanswers;
	}

	public boolean unAnsweredQuestions(String userName) {
		boolean unAnswered = false;
		int answered = 0;
		Map<String, List<Multiplechoiceanswers>> multichoice = allMultipleChoiceAnswers.get(userName);
		if ((multichoice == null) || (multichoice.isEmpty())) {
			unAnswered = true;
		} else {
			Set<Map.Entry<String, List<Multiplechoiceanswers>>> choiceentrySet = multichoice.entrySet();
			for (Map.Entry<String, List<Multiplechoiceanswers>> mapchoiceentry : choiceentrySet) {
				List<Multiplechoiceanswers> Mchoiceanswers = mapchoiceentry.getValue();
				answered = 0;
				for (Multiplechoiceanswers mchoice : Mchoiceanswers) {
					if (mchoice.getCandidateanswer()) {
						answered++;
						break;
					}
				}
				if (answered == 0) {
					unAnswered = true;
					break;
				} else {
					unAnswered = false;
				}
			}
		}
		return unAnswered;
	}
//
	public int CountAllQuestions(String userName) {
		Map<Integer, Questions> questionsList = allcandidateQuestions.get(userName);
		if ((questionsList == null) || (questionsList.isEmpty())) {
			return 0;
		} else {
			return questionsList.size();
		}
	}

	//
	public Questions getQuestion(String userName) {
		Map<Integer, Questions> questionsList = allcandidateQuestions.get(userName);
		if ((questionsList == null) || (questionsList.isEmpty())) {
			return new Questions();
		} else {
			return questionsList.get(allcandidateinfo.get(userName));
		}
	}
//
	public Map<String, Multiplechoiceanswers> getAnswers(String userName, String title) {
		Map<String, List<Multiplechoiceanswers>> multichoice = allMultipleChoiceAnswers.get(userName);
		if ((multichoice == null) || (multichoice.isEmpty())) {
			logger.info("getAnswers() invoked :- Multiple Choice answers not found for Question UserName and title :- "+userName+", "+title);
			return new HashMap<>();
		} else {
			List<Multiplechoiceanswers> choiceAnswers = multichoice.get(title);
			Map<String, Multiplechoiceanswers> mappedAnswer = new HashMap<>();
			for (Multiplechoiceanswers mchoice : choiceAnswers) {
				mappedAnswer.put(mchoice.getDisplayorder(), mchoice);
			}
			logger.info("getAnswers() invoked :- Multiple Choice answers found for Question UserName and title :- "+userName+", "+title);
			Map<String, Multiplechoiceanswers> sortedAnswersByKey = mappedAnswer.entrySet().stream()
					.sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
							(v1, v2) -> v1, LinkedHashMap::new));
			return sortedAnswersByKey;
		}
	}

	// updateAnswers
	public boolean updateAnswers(String userName, Map<String, Boolean> userAnswers) {
		// return allcandidateinfo.get(userName);
		boolean updatesuccess = false;
		List<Multiplechoiceanswers> newChoiceAnswers = new ArrayList<>();
		logger.info("updateAnswers() invoked:  Just entered into updateAnswer method");
		if ((userAnswers != null) && (!userAnswers.isEmpty())) {
			logger.info("updateAnswers() invoked:  The Answer List is not Null or empty " );
			if (getQuestionNo(userName) != 0) {
				logger.info("updateAnswers() invoked:  Question number extracted and is not equal to 0");
				Map<String, List<Multiplechoiceanswers>> multichoice = allMultipleChoiceAnswers.get(userName);
				if ((multichoice != null) && (!multichoice.isEmpty())) {
					String questTitle = getQuestion(userName).getTitle();
					List<Multiplechoiceanswers> choiceAnswers = multichoice.get(questTitle);
					logger.info("updateAnswers() invoked:  Multiple Choice Answer Read and Ready for processing");
					for (Multiplechoiceanswers mchoice : choiceAnswers) {
						Boolean answerfound = userAnswers.get(mchoice.getDisplayorder());
						if (answerfound != null) {
							mchoice.setCandidateanswer(answerfound);
						}
						newChoiceAnswers.add(mchoice);
						logger.info("updateAnswers() invoked:  Answer Update " + userName+", "+mchoice.getCandidateanswer());
					}
					if ((newChoiceAnswers != null) && (!newChoiceAnswers.isEmpty())) {
						logger.info("updateAnswers() invoked:  About To Cache Answer List Update" + userName);
						multichoice.put(questTitle, newChoiceAnswers);
						allMultipleChoiceAnswers.put(userName, multichoice);
						logger.info("updateAnswers() invoked:  User Answer Cache List Update" + userName);
					} 
				} else {
					logger.info("updateAnswers() invoked:  Answers not updated No List extracted from server for answer data List: " + userName);					
				}
			}
		} else {
			logger.info("updateAnswers() invoked:  Answers not updated due to null answer data List: " + userName);
		}
		return updatesuccess;
	}
//
	public int examClosingUpdate(String userName) {
		Map<String, List<Multiplechoiceanswers>> multichoice = allMultipleChoiceAnswers.get(userName);
		int correctAns = 0;
		if ((multichoice == null) || (multichoice.isEmpty())) {
		} else {
			logger.info("ExamCloseAnswerUpdate() invoked:  Name Found in Multiplechoiceanswers List : " + userName);
			Set<Map.Entry<String, List<Multiplechoiceanswers>>> choiceentrySet = multichoice.entrySet();
			for (Map.Entry<String, List<Multiplechoiceanswers>> mapchoiceentry : choiceentrySet) {
				List<Multiplechoiceanswers> Mchoiceanswers = mapchoiceentry.getValue();
				int missedAnswer = 0;
				for (Multiplechoiceanswers mchoice : Mchoiceanswers) {
					// update database with mchoice
					if (((mchoice.getCandidateanswer() == true) & (mchoice.getCorrectanswer() == false))
							|| ((mchoice.getCandidateanswer() == false) & (mchoice.getCorrectanswer() == true))) {
						missedAnswer++;
					}
					if (mchoice.getCandidateanswer()) {
						try {
							String title = mchoice.getTitle();
							Multiplechoiceanswers manswers = restDataRepository.updateMultiplechoiceanswers(userName,title, mchoice);
							if ((manswers == null) || (manswers.getId() == null)) {
								logger.info("ExamCloseAnswerUpdate() invoked:  Object for Multiplechoiceanswers Not updated : "+ userName + " : " + title + " : " + mchoice.getDisplayorder());
							} else {
								logger.info("ExamCloseAnswerUpdate() invoked:  Updated Object for Multiplechoiceanswers : "+ userName + " : " + title + " : " + mchoice.getDisplayorder());
							}
						} catch (HttpClientErrorException e) { // 404
							e.printStackTrace();
						}
					}
				}
				if (missedAnswer == 0) {
					correctAns++;
				}
			}
		}
		setCandidateScore(userName, correctAns);
		return correctAns;
	}
	//
	public String getTimeRemain(String userName) {
		Testexam testexam = getTestExamByUserName(userName);
		String remaining ="";
		String remaining2 ="";
		if ((testexam == null) || (testexam.getId() == null)) {
			logger.info("g etTimeRemain() invoked:  Error unable to read TestExam entity : " + userName);
		} else {
			Long startT = testexam.getStartdatetime().getTime();
			Long duraT = testexam.getDurationmillsec().longValue();
			Long now = new Date().getTime();
			Long timeremain = (startT+duraT) - now;
			if (timeremain > 0){
			remaining2 =String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeremain),
					TimeUnit.MILLISECONDS.toMinutes(timeremain) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeremain)), 
					TimeUnit.MILLISECONDS.toSeconds(timeremain) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeremain)));
	        //double days = Math.floor(timeremain / (1000 * 60 * 60 * 24));
	        double hours = Math.floor((timeremain % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
	        double minutes = Math.floor((timeremain % (1000 * 60 * 60)) / (1000 * 60));
	        double seconds = Math.floor((timeremain % (1000 * 60)) / 1000);
			remaining = twoDigitString(hours)+":"+twoDigitString(minutes)+":"+twoDigitString(seconds);
			//logger.info("getTimeRemain() invoked:  Testexam object read, time format is : "+ remaining);
			//logger.info("getTimeRemain() invoked:  Testexam object read, time format is : "+ remaining2);
			}else{
				remaining2 = "TimeOut";
			}
		}
		return remaining2;	
	}
	//
	public Testexam updateStartTime(String userName) {
		Testexam testexam = getTestExamByUserName(userName);
		Testexam returns = new Testexam();
		if ((testexam == null) || (testexam.getId() == null)) {
			logger.info("updateStartTime() invoked:  Error unable to read TestExam entity : " + userName);
		} else {
			String dura = testexam.getExamtime();
			if (dura != null) {
				int hrs = 0;
				int mins = 0;
				if (dura.split(",").length > 1) {
					hrs = Integer.parseInt(dura.split(",")[0]);
					mins = Integer.parseInt(dura.split(",")[1]);
				} else {
					hrs = Integer.parseInt(dura.split(",")[0]);
				}
				testexam.setDurationmillsec(ConvertHoursMinutes(hrs, mins));
			} else {
				testexam.setDurationmillsec(ConvertHoursMinutes(1, 0));
			}
			testexam.setStartdatetime(new Date());
			logger.info("updateStartTime() invoked:  Testexam object read and Updated ready to be saved : " + userName);
			try {
				returns = restDataRepository.updateTestExam(userName, testexam);
				if ((returns == null) || (returns.getId() == null)) {
					logger.info("updateStartTime() invoked:  Object for Testexam Not updated : " + userName);
					returns = new Testexam();
				} else {
					logger.info("updateStartTime() invoked:  Updated Object for Testexam : " + userName);
				}
			} catch (HttpClientErrorException e) { // 404
				e.printStackTrace();
			}
		}
		return returns;
	}
	//
	public Testexam updateTestExam(String userName) {
		Testexam testexam = getTestExamByUserName(userName);
		Testexam returns = new Testexam();
		if ((testexam == null) || (testexam.getId() == null)) {
			logger.info("ExamCloseAnswerUpdate() invoked:  Error unable to read TestExam entity : " + userName);
		} else {
			testexam.setCorrectanswer(getCandidateScore(userName));
			testexam.setTotalquestions(CountAllQuestions(userName));
			BigDecimal totalscore = new BigDecimal(((getCandidateScore(userName) / CountAllQuestions(userName))) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
			testexam.setTotalscore(totalscore);
			logger.info("ExamCloseAnswerUpdate() invoked:  Testexam object read and Updated ready to be saved : "+ userName);
			try {
				returns = restDataRepository.updateTestExam(userName, testexam);
				// returns = testexam;
				if ((returns == null) || (returns.getId() == null)) {
					logger.info("ExamCloseAnswerUpdate() invoked:  Object for Testexam Not updated : " + userName);
					returns = new Testexam();
				} else {
					logger.info("ExamCloseAnswerUpdate() invoked:  Updated Object for Testexam : " + userName);
				}
			} catch (HttpClientErrorException e) { // 404
				e.printStackTrace();
			}
		}
		return returns;
	}
   //
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
	//
	public String twoDigitString(Double timing){
		Integer memint = timing.intValue();
		String varmem = memint.toString().trim();
		if (varmem.length() == 1){
			varmem = "0"+varmem;
		}	
		return varmem;
	}
	//
	public void setCandidateScore(String userName, int score) {
		if (allcandidateScore.get(userName) != null) {
			this.allcandidateScore.put(userName, score);
		} else {
			logger.info("setCandidateScore() invoked:  Error User Does not Exist : " + userName);
		}
	}
//
	public int getCandidateScore(String userName) {
		if (allcandidateScore.get(userName) != null) {
			return allcandidateScore.get(userName);
		} else {
			logger.info("getCandidateScore() invoked:  Error User Does not Exist : " + userName);
			return 0;
		}
	}
//
	public int getQuestionNo(String userName) {
		logger.info("getQuestionNo() invoked:  Total Number of questions for candiate " + userName+" = "+CountAllQuestions(userName));
		if (allcandidateinfo.get(userName) != null) {
			return allcandidateinfo.get(userName);
		} else {
			logger.info("getQuestionNo() invoked:  Error User Does not Exist : " + userName);
			return 0;
		}
	}

	public boolean setQuestionNo(String userName, int questionNo) {
		if (allcandidateinfo.get(userName) != null) {
			this.allcandidateinfo.put(userName, questionNo);
		} else {
			logger.info("setQuestionNo() invoked:  Error User Does not Exist : " + userName);
		}
		return true;
	}

	public Map<String, Integer> getAllcandidateScore() {
		return allcandidateScore;
	}

	public void setAllcandidateScore(Map<String, Integer> allcandidateScore) {
		this.allcandidateScore = allcandidateScore;
	}

	public Map<String, Integer> getAllcandidateinfo() {
		return allcandidateinfo;
	}

	public void setAllcandidateinfo(Map<String, Integer> allcandidateinfo) {
		this.allcandidateinfo = allcandidateinfo;
	}

	public Map<String, Map<Integer, Questions>> getAllcandidateQuestions() {
		return allcandidateQuestions;
	}

	public void setAllcandidateQuestions(Map<String, Map<Integer, Questions>> allcandidateQuestions) {
		this.allcandidateQuestions = allcandidateQuestions;
	}

	public Map<String, Map<String, List<Multiplechoiceanswers>>> getAllMultipleChoiceAnswers() {
		return allMultipleChoiceAnswers;
	}

	public void setAllMultipleChoiceAnswers(
			Map<String, Map<String, List<Multiplechoiceanswers>>> allMultipleChoiceAnswers) {
		this.allMultipleChoiceAnswers = allMultipleChoiceAnswers;
	}

}
