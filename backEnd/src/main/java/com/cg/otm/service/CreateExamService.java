package com.cg.otm.service;

import java.util.List;

import com.cg.otm.entities.Category;
import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;

public interface CreateExamService {

	public boolean addCategory(Category category);
	public Category getCategory(Long categoryId);
	public Category getCategoryByName(String name);
	public List<Category> getAllCategory();
	public boolean removeCategory(String name);

	public boolean addTest(Test test);
	public boolean deleteTest(long testId);
	public boolean updateTest(long testId,Test test);
	public List<Test> getAllTests();
	public boolean addQuestionToTest(long questionId, long testId);
	public boolean deleteQuestionFromTest(long questionId, long testId);
	public Test getTestByName(String name);
	public Test getTest(Long testId);
	public List<String> getAllTitles();

	public List<Question> getRemainingQuestion();
	public Question getQuestionByName(String name);
	public Question getQuestion(Long questionId);
	public boolean addQuestion(Question question, long cat_id);
	public List<Question> getAllQuestion();
	public Boolean deleteQuestion(Long questionId);
	public boolean updateQuestion(Question question);
	public List<Question> getTestQuestions(long testId);

	public List<User> getAllUsers();
	public void setMarks(long testId);
}
