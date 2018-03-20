package com.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Questions;
import com.exceptions.DisplayRecordNotFoundError;
import com.exceptions.RecordNotFoundException;
import com.repository.QuestionsRepository;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author Paul Chapman
 */
@RestController
public class QuestionsController {

	protected Logger logger = Logger.getLogger(QuestionsController.class.getName());
	protected QuestionsRepository questionsRepository;

	/**
	 * Create an instance plugging in the respository of Accounts.
	 * 
	 * @param accountRepository
	 *            An account repository implementation.
	 */
	@Autowired
	public QuestionsController(QuestionsRepository questionsRepository) {
		this.questionsRepository = questionsRepository;
	}

	/**
	 * Fetch an account with the specified account number.
	 * 
	 * @param accountNumber
	 *            A numeric, 9 digit account number.
	 * @return The account if found.
	 * @throws RecordNotFoundException
	 *             If the number is not recognised.
	 */
	@RequestMapping("/Questions/{userName}")
	public List<Questions> byUserName(@PathVariable("userName") String userName) {
		List<Questions> questions = questionsRepository.findByUsername(userName);
		if (questions.isEmpty()) {
			DisplayRecordNotFoundError derror = new DisplayRecordNotFoundError();
			derror.logerror("Questions", "Username", userName);
		}
		return questions;
	}

	@RequestMapping(path = "/Questions/{userName}/{title}", method = RequestMethod.GET)
	public Questions byTitleAndUserName(@PathVariable("userName") String userName,
			@PathVariable("title") String title) {
		Questions questions = questionsRepository.findByUsernameAndTitle(userName, title);
		if (questions == null || questions.getId() == null) {
			DisplayRecordNotFoundError derror = new DisplayRecordNotFoundError();
			derror.logerror("Questions", "Username", userName);
			return new Questions();
		} else {
			return questions;
		}
	}
}
