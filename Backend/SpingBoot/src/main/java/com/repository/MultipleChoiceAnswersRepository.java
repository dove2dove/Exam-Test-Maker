package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Multiplechoiceanswers;

/**
 * Repository for Account data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
public interface MultipleChoiceAnswersRepository extends JpaRepository<Multiplechoiceanswers, Integer> {

	public List<Multiplechoiceanswers> findByUsernameAndTitle(String userName, String title);
	public List<Multiplechoiceanswers> findByUsername(String userName);
}
