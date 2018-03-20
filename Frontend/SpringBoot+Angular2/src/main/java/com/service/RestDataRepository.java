package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.client.RestTemplate;

import com.domain.Examparameters;
import com.domain.Multiplechoiceanswers;
import com.domain.Questions;
import com.domain.Testexam;
import com.domain.User;

@Component
public class RestDataRepository {
	
	@Autowired
	protected RestTemplate restTemplate;
	protected String serviceUrl;
	protected Logger logger = Logger.getLogger(RestDataRepository.class.getName());
	
	public RestDataRepository() {
		serviceUrl = "http://localhost:8089";
	}

	@PostConstruct
	public void demoOnly() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		logger.warning("The RestTemplate request factory is " + restTemplate.getRequestFactory().getClass());
	}

	public Examparameters getExamParameterByExamCode(String examCode) {
		logger.info("RestDataRepository: getExamParameterByExamCode() invoked : "+examCode);
		Examparameters examparameters = null;
		try{
			examparameters = restTemplate.getForObject(serviceUrl + "/parameter/{examCode}", Examparameters.class, examCode);
			logger.info("getExamParameterByExamCode() invoked restTemplate successfully read data from webservice");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return examparameters;
	}

	public User getUserByUserName(String userName) {
		return restTemplate.getForObject(serviceUrl + "/user/{userName}", User.class, userName);
	}

	public Testexam getTestExamByUserName(String userName) {
		return restTemplate.getForObject(serviceUrl + "/Testexam/{userName}", Testexam.class, userName);
	}

	public Questions[] getQuestionsListByUserName(String userName) {
		return restTemplate.getForObject(serviceUrl + "/Questions/{userName}", Questions[].class, userName);
	}

	public Questions getQuestionByUserNameAndTitle(String userName, String title) {
		return restTemplate.getForObject(serviceUrl + "/Questions/{userName}/{title}", Questions.class, userName,
				title);
	}

	public Map<String, List<Multiplechoiceanswers>> getAnswersByUsername(String userName) {
		Map<String, List<Multiplechoiceanswers>> multichoice = new HashMap<>();
		TreeMap<String, Multiplechoiceanswers> tmap = new TreeMap<>();
		Multiplechoiceanswers[] ansArray = restTemplate.getForObject(serviceUrl + "/multiplechoice/{userName}",
				Multiplechoiceanswers[].class, userName);
		if (ansArray.length > 0) {
			for (int k = 0; k < ansArray.length; k++) {
				tmap.put(ansArray[k].getTitle() + "-" + ansArray[k].getDisplayorder(), ansArray[k]);
			}
			String prevkey = "";
			String newkey = " ";
			List<Multiplechoiceanswers> AnsList = new ArrayList<>();
			Set<String> keys = tmap.keySet();
			for (String key : keys) {
				newkey = key.split("-")[0];
				if (prevkey.equals("")) {
					AnsList = new ArrayList<>();
					AnsList.add(tmap.get(key));
					prevkey = newkey;
				} else {
					if (newkey.equals(prevkey)) {
						AnsList.add(tmap.get(key));
					} else {
						multichoice.put(prevkey, cloneAnswerList(AnsList));
						AnsList = new ArrayList<>();
						AnsList.add(tmap.get(key));
						prevkey = newkey;
					}
				}
			}
			multichoice.put(newkey, cloneAnswerList(AnsList));
		}
		return multichoice;
	}

	public Multiplechoiceanswers updateMultiplechoiceanswers(String userName, String title,
			Multiplechoiceanswers mchoice) {
		return restTemplate.postForObject(serviceUrl + "/multiplechoice/save", mchoice, Multiplechoiceanswers.class,
				userName, title);
	}

	public Testexam updateTestExam(String userName, Testexam testexam) {
		logger.info("RestDataRepository: updateTestExam() invoked : "+userName);
		Testexam testexam2 = null;
		try{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Testexam> entity = new HttpEntity<>(testexam,headers);
			logger.info("updateTestExam() invoked :- "+testexam.getId()+", "+testexam.getUsername()+", "+testexam.getExamcode()+", "+testexam.getDurationmillsec()+", "+testexam.getStartdatetime());
			testexam2 = restTemplate.postForObject(serviceUrl + "/Testexam/save", entity, Testexam.class, userName);
			logger.info("updateTestExam() invoked restTemplate successfully read data from webservice");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return testexam2;
	}

	private List<Multiplechoiceanswers> cloneAnswerList(List<Multiplechoiceanswers> ansList) {
		List<Multiplechoiceanswers> tempList = new ArrayList<>();
		for (Multiplechoiceanswers ansvar : ansList) {
			tempList.add(ansvar);
		}
		return tempList;
	}
}
