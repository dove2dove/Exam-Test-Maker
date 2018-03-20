package com.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.User;
import com.exceptions.DisplayRecordNotFoundError;
import com.exceptions.RecordNotFoundException;
import com.repository.UserRepository;

/**
 * A RESTFul controller for accessing account information.
 * 
 * @author Paul Chapman
 */
@RestController
public class UserController {

	protected Logger logger = Logger.getLogger(UserController.class.getName());
	protected UserRepository userRepository;

	/**
	 * Create an instance plugging in the respository of Accounts.
	 * 
	 * @param accountRepository
	 *            An account repository implementation.
	 */
	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
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
	@RequestMapping("/user/{userName}")
	public User byUserName(@PathVariable("userName") String userName) {
		User user = userRepository.findByUsername(userName);
		if (user == null || user.getId() == null) {
			DisplayRecordNotFoundError derror = new DisplayRecordNotFoundError();
			derror.logerror("User", "Username", userName);
			return new User();
		} else {
			return user;
		}
	}
}
