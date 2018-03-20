package com.domain;

import java.util.Map;

public class AnswerResponse {
	private String userName;
	private String questionNo;
	private String preQuestionNo;
	private Map<String, Boolean> Answers;
	private Boolean userChoice;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getQuestionNo() {
		return questionNo;
	}
	public void setQuestionNo(String questionNo) {
		this.questionNo = questionNo;
	}
	public String getPreQuestionNo() {
		return preQuestionNo;
	}
	public void setPreQuestionNo(String preQuestionNo) {
		this.preQuestionNo = preQuestionNo;
	}
	public Map<String, Boolean> getAnswers() {
		return Answers;
	}
	public void setAnswers(Map<String, Boolean> answers) {
		Answers = answers;
	}
	public Boolean getUserChoice() {
		return userChoice;
	}
	public void setUserChoice(Boolean userChoice) {
		this.userChoice = userChoice;
	}


}
