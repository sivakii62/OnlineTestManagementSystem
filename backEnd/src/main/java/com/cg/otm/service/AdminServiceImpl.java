package com.cg.otm.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.otm.dao.AdminDaoImpl;
import com.cg.otm.entities.Category;
import com.cg.otm.entities.CategoryResult;
import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.exceptions.CannotRetrieveDataException;
import com.cg.otm.exceptions.NoDataFoundedException;

@Service
public class AdminServiceImpl implements AdminServiceInterface{

	@Autowired
	private AdminDaoImpl dao;
	
	
	/*
	 * Get All users to view and edit
	 * 
	 * 
	 * */
	
	
	public List<User> getAllUsers() throws CannotRetrieveDataException {
		return dao.getAllUsers();
	}
	
	
	
	/*Get All Tests To view or edit*/
	
	public List<Test> getAllTests() throws CannotRetrieveDataException {
		return dao.getAllTests();
	}
	
	public List<User_Test> getAllUserTests()  throws Exception
	{
		List<User_Test> list  =null;
		try {
		 list = dao.getUserTestAllIsNotDeclared();
		list.get(0);
	}
		catch(Exception ex)
		{
			throw new NoDataFoundedException("all tests are declared (or) no tests available");
		}
		return list;
	}
	
    /*Getting all exams of all users*/

	public List<User_Test> allExams() throws Exception {
		return dao.allExams();
	}
	
	
	
	/*Get score of a particular user's exam from its userTestID */

	
	public Long calculateScoreService(Long userTestId) throws Exception {
		
		Long score=0L;
		
		User_Test userTest =  dao.getUserTest(userTestId);

		List<Boolean> answersCorrectedList = userTest.getTestCorrectAnswer();
		List<Integer> optionList = userTest.getUsertestAnswer();
		List<Question> questionsList = userTest.getTest().getAllQuestion();
		
		answersCorrectedList.clear();
		int currentQuestion = 0;
		for(Integer option: optionList) 
		{
			if(option == 0) 
			{
				answersCorrectedList.add(null);
			}
			else if(option == questionsList.get(currentQuestion).getQuestionAnswer()) 
			{
				answersCorrectedList.add(true);
				Long marks = questionsList.get(currentQuestion).getQuestionMarks();
				score += marks;
			}
			else 
			{
				answersCorrectedList.add(false);
				
			}
			currentQuestion++;
			
		}
		userTest.setMarksScored(score);
		userTest.setTestCorrectAnswer(answersCorrectedList);
		userTest.setDeclared(true);
		categoryScore(userTestId);
		dao.setScore(userTest);
		
		return score;
	}


	
	
	/*Get score of a particular user's exam by category of questions from its userTestID */

	public List<CategoryResult> categoryScore(Long userTestId) throws Exception{
		User_Test userTest = dao.getUserTest(userTestId);
		List<Question> questionsList = userTest.getTest().getAllQuestion();
		List<Boolean> answeredCorrectList = userTest.getTestCorrectAnswer();
		
		Set<Category> categorySet  = new HashSet<Category>();
		for(Question question: questionsList)
		{
			Category category = question.getQuestionCategory();
			categorySet.add(category);
		}
		
		int answerListIteration = 0;
		Long attemptedQuestions = 0L;
		Long categoryScore = 0L;
		
		List<CategoryResult> categoryResultList = new ArrayList<CategoryResult>();
		
		for(Category category : categorySet)
		{
			CategoryResult categoryResult = new CategoryResult();
			answerListIteration=0;
			attemptedQuestions = 0L;
			categoryScore = 0L;
			for(Question question: questionsList)
			{
				if(category.getCategoryId()  == question.getQuestionCategory().getCategoryId()) {
					
					if(answeredCorrectList.get(answerListIteration) == null)
					{
						//doing nothing
					}
					else if(answeredCorrectList.get(answerListIteration) == true)
					{
						attemptedQuestions++;
						categoryScore += question.getQuestionMarks(); 
					}
					else if(answeredCorrectList.get(answerListIteration) == false)
					{
						attemptedQuestions++;
					}
				}
				answerListIteration++;
			}
			categoryResult.setAttemptedQuestions(attemptedQuestions);
			categoryResult.setCategoryResult(categoryScore);
			categoryResult.setCategory(category);
			categoryResult.setUserTest(userTest);
			
			dao.setCategoryResult(categoryResult);
			
			categoryResultList.add(categoryResult);
			
			
		}
		
		return categoryResultList;
	}

	
	
	
	
	/*
	 * 
	 * Assigning Test to a user by his userId*/
	
	
	public boolean assignTest(long testId, long userId) {
		return dao.assignTest(testId, userId);
	}
	
	
	
	
	

	public List<User_Test> getTests() throws Exception {
		
			return dao.getTests();
		
	}
	
	
	
	
	
	/*
	 * Top performers
	 * 
	 * 
	 */
	
    /*Getting the top performer by considering all tests*/
	
	public List<User_Test> topPerformers() throws Exception {
		return dao.topPerformers();
	}
	
	
	/*Getting the top performer by considering a particular test*/

	public List<User> topPerformersOfTest(long testId) throws Exception {
		return dao.topPerformersOfTest(testId);
	}
	
	
	
	
	
	/*Getting the top 10 performers average results*/
	
	
	
	public HashMap<Long, Avg> topPerformersAvg() throws Exception {
		List<User_Test> user_Test = dao.topPerformersAvg();
		HashMap<Long, Avg> data = new HashMap<Long, Avg>();

		user_Test.forEach(ob -> {
			User u = ob.getUser();
			if (data.containsKey(u)) {
				data.put(u.getUserId(), new Avg(data.get(u).getMarks() + ob.getMarksScored(),
						data.get(u).getCount() + 1, data.get(u).getUserName()));
			}

			else
				data.put(u.getUserId(), new Avg(ob.getMarksScored(), 1, u.getUserName()));
		});

		// Sorting the map
		List<Map.Entry<Long, Avg>> list = new LinkedList<Map.Entry<Long, Avg>>(data.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<Long, Avg>>() {
			public int compare(Map.Entry<Long, Avg> o1, Map.Entry<Long, Avg> o2) {
				return (o1.getValue().getAvg() > o2.getValue().getAvg() ? -1 : 1);
			}
		});

		// put data from sorted list to hashmap
		HashMap<Long, Avg> sortedAvgMap = new LinkedHashMap<Long, Avg>();
		for (Map.Entry<Long, Avg> aa : list) {
			sortedAvgMap.put(aa.getKey(), aa.getValue());
		}

		return sortedAvgMap;
	}
	
	
	
	 /*Getting the total questions per category from all questions in the database*/

		public HashMap<String, Long> questionsCategory() throws NoDataFoundedException {
			return dao.questionsCategory();
		}
	
	

	public long getTotalUsers() throws CannotRetrieveDataException {
		return dao.getTotalUsers();
	}
	
	


	public long getTotalTests() throws CannotRetrieveDataException {
		return dao.getTotalTests();
	}

	public long getTotalQuestions() throws CannotRetrieveDataException {
		return dao.getTotalQuestions();
	}

	
	  
	

	
	
	
	
	
	

//	public boolean deassignTest(long testId, long userId) throws Exception {
//		return dao.deassignTest(testId, userId);
//	}
	
	
	
	
	
	

}
class Avg {
	long marks;
	long count;
	String userName;

	public Avg(long marks, long count) {
		super();
		this.marks = marks;
		this.count = count;
	}

	public Avg(String userName) {
		super();
		this.userName = userName;
	}

	public long getMarks() {
		return marks;
	}

	public void setMarks(long marks) {
		this.marks = marks;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	float getAvg() {
		return this.marks / this.count;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Avg(long marks, long count, String userName) {
		super();
		this.marks = marks;
		this.count = count;
		this.userName = userName;
	}

}
