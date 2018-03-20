package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Questions;



/**
 * Repository for Account data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
public interface QuestionsRepository extends JpaRepository<Questions, Integer> {

	public List<Questions> findByUsername(String userName);
	public Questions findByUsernameAndTitle(String userName, String title);
}
