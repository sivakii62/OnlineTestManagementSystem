package com.cg.otm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.cg.otm.entities.Category;
import com.cg.otm.entities.Question;

public interface QuestionRepository extends JpaRepository<Question,  Long> {

	@Query("Select question from Question question")
	List<Question> allQuestions();
	
	@Query("select question from Question question where question.questionTitle=:name")
	List<Question> findByName(@Param("name") String name);
	

	
}
