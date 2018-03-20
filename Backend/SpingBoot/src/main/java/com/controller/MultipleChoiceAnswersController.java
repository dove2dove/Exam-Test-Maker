package com.controller;

import java.util.List;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.domain.Multiplechoiceanswers;
import com.exceptions.DisplayRecordNotFoundError;
import com.exceptions.RecordNotFoundException;
import com.repository.MultipleChoiceAnswersRepository;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author Paul Chapman
 */
@RestController
public class MultipleChoiceAnswersController {

	protected Logger logger = Logger.getLogger(MultipleChoiceAnswersController.class.getName());
	protected MultipleChoiceAnswersRepository multipleChoiceAnswersRepository;

	/**
	 * Create an instance plugging in the respository of Accounts.
	 * 
	 * @param accountRepository
	 *            An account repository implementation.
	 */
	@Autowired
	public MultipleChoiceAnswersController(MultipleChoiceAnswersRepository multipleChoiceAnswersRepository) {
		this.multipleChoiceAnswersRepository = multipleChoiceAnswersRepository;
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
	@RequestMapping("/multiplechoice/{userName}")
	public List<Multiplechoiceanswers> byUserName(@PathVariable("userName") String userName) {
		List<Multiplechoiceanswers> multipleChoiceAnswers = multipleChoiceAnswersRepository.findByUsername(userName);
		if (multipleChoiceAnswers.isEmpty()) {
			DisplayRecordNotFoundError derror = new DisplayRecordNotFoundError();
			derror.logerror("Multiplechoiceanswers", "Username", userName);			
		}
		return multipleChoiceAnswers;
	}

	@RequestMapping(value = "/multiplechoice/save", method = RequestMethod.POST)
	public Multiplechoiceanswers bySaveEntity(@Valid @RequestBody Multiplechoiceanswers request) {
		logger.info("bySaveEntity() invoked: About to save Single  Multiplechoiceanswers");
		if (request == null || request.getId() == null) {
			DisplayRecordNotFoundError derror = new DisplayRecordNotFoundError();
			derror.logerror("Multiplechoiceanswers", "Entity", "Saving");
			return new Multiplechoiceanswers();
		} else {
			logger.info("bySaveEntity() invoked: saved Single  Multiplechoiceanswers");
			return multipleChoiceAnswersRepository.saveAndFlush(request);
		}
	}

}
