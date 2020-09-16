package com.cg.otm.dao;

import java.util.List;

import com.cg.otm.entities.Category;
import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;

public interface CreateExamDao {

	public boolean addCategory(Category category) ;
	public Category getCategory(Long categoryId) ;
	public boolean removeCategory(Category category);
	public List<Category> getAllCategory();
	public Category getCategoryByName(String name);

	public Question getQuestion(Long questionId) ;
	public boolean addQuestion(Question question, Category category) ;
	public boolean deleteQuestion(Question question) ;
	public boolean updateQuestion(Question question) ;
	public List<Question> getAllQuestions() ;
	public Question getQuestionByName(String name);

	public Test getTestByName(String name);
	public boolean addTest(Test test);
	public boolean deleteTest(Test test);
	public boolean updateTest(Test test);			
	public List<Test> getAllTests() ;
	public Test getTest(Long testId) ;


	public List<User> getAllUsers();



}
