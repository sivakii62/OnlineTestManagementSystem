package com.cg.otm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.otm.entities.Category;
import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.repository.CategoryRepository;
import com.cg.otm.repository.QuestionRepository;
import com.cg.otm.repository.TestRepository;
import com.cg.otm.repository.UsersRepository;

@Repository
@Transactional
public class CreatExamDaoImpl implements CreateExamDao{

	@Autowired
	@PersistenceContext
	EntityManager em;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	TestRepository testRepository;

	@Autowired
	UsersRepository userRepository;
	
	@Override
	public boolean addCategory(Category category) {
		categoryRepository.save(category);
		return true;
	}

	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public List<Category> getAllCategory() {
		
		return categoryRepository.getAll();
	}	
	@Override
	public boolean removeCategory(Category category) {
		categoryRepository.delete(category);
		return true;
	}	

	@Override
	public boolean addQuestion(Question question, Category category) {	

		questionRepository.save(question);
		return true;
		
	}

	@Override
	public List<Question> getAllQuestions() {
		
		return questionRepository.allQuestions();
	}
	
	@Override
	public boolean deleteQuestion(Question question) {
		questionRepository.delete(question);
		return true;
	}

	@Override
	public boolean updateQuestion(Question question) {
		questionRepository.save(question);
		return true;
	}

	@Override
	public boolean addTest(Test test){
		testRepository.save(test);
		return true;
	}
		
	@Override
	public boolean deleteTest(Test test) {
		testRepository.delete(test);
		return true;
	}

	@Override
	public boolean updateTest(Test test) {			
		testRepository.save(test);
		return true;
	}

	@Override
	public List<Test> getAllTests() {
		return testRepository.allTests();
	}
				
	@Override
	public Category getCategory(Long categoryId) {
		Category category= categoryRepository.getOne(categoryId);
		return category;
	}

	@Override
	public Category getCategoryByName(String name) {
		Category category= categoryRepository.findByName(name).get(0);
		return category;
	}

	@Override
	public Test getTestByName(String name) {
		Test test= testRepository.findByName(name).get(0);
		return test;
	}
	@Override
	public Question getQuestionByName(String name) {
		Question question= questionRepository.findByName(name).get(0);
		return question;
	}

	@Override
	public Question getQuestion(Long questionId) {
		Question question = questionRepository.getOne(questionId);
		return question;
	}

	@Override
	public Test getTest(Long testId) {
		Test test = testRepository.getOne(testId);
		return test;
	}
}