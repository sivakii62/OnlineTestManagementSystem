package com.cg.otm.dao;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cg.otm.entities.Category;
import com.cg.otm.entities.CategoryResult;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.exceptions.CannotRetrieveDataException;
import com.cg.otm.exceptions.DataEnteringException;
import com.cg.otm.exceptions.DataMergingException;
import com.cg.otm.exceptions.DataMismatchExcpetion;
import com.cg.otm.exceptions.NoDataFoundedException;
import com.cg.otm.repository.UserTestRepository;


@Repository
@Transactional
public class AdminDaoImpl  implements AdminDaoInterface{

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private UserTestRepository userTestRepo;
	
	
	/*
	 * Get All users to view and edit
	 * 
	 * 
	 * */
	
	
	public List<User> getAllUsers() throws CannotRetrieveDataException {
		try {
			String statement = "SELECT user FROM User user WHERE isAdmin=0";
			TypedQuery<User> query = entityManager.createQuery(statement, User.class);
			List<User> allUsers = query.getResultList();
				
			return allUsers;
		}
		catch(Exception exception) {
			throw new CannotRetrieveDataException(exception.getMessage());
		}
	}
	
	
	
/*Get All Tests To view or edit*/
	
	public List<Test> getAllTests() throws CannotRetrieveDataException {
		try {
			String statement = "SELECT test FROM Test test ";
			TypedQuery<Test> query = entityManager.createQuery(statement, Test.class);
			List<Test> allTests = query.getResultList();
			
			return allTests;
		}
		catch(Exception exception) {
			throw new CannotRetrieveDataException(exception.getMessage());
		}
	}
	
	
	
	
	
    /*Getting all exams of all users*/

	
	public List<User_Test> allExams() throws Exception {
		try {
			String statement = "SELECT ut FROM User_Test ut";
			TypedQuery<User_Test> query = entityManager.createQuery(statement, User_Test.class);
			List<User_Test> user_Test = query.getResultList();
			if(user_Test.size() == 0) {
				throw new NoDataFoundedException("No Details is founded in USER_TEST table...");
			}
			
				
			return user_Test;
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage());
		}
	}
	
	
	
	/*To find the userTest from its id*/
	public User_Test getUserTest(long userTestId) throws Exception{
		try {
			return entityManager.find(User_Test.class, userTestId);
		}
		catch(Exception e)
		{
			throw new NoDataFoundedException("No Data Available in database...");
		}
	}
	

	public List <User_Test> getUserTestAllIsNotDeclared() {
		
		List <User_Test>  list = userTestRepo.findAll().stream()
				.filter(x-> !x.isDeclared() && x.isAttempted()).collect(Collectors.toList());
		
		return list;
	}
	
	
	
     /*To set the score of a particular userTest*/
	
	public boolean setScore(User_Test userTest) throws Exception{
		try {
			entityManager.merge(userTest);
			return true;
		}
		catch(Exception e) {
			throw new DataMergingException("Data cannot be merged...");
		}
	}
	
	
	
	/*Store the category result in database */

	public void setCategoryResult(CategoryResult categoryResult) throws Exception{
		try {
			entityManager.persist(categoryResult);
		}
		catch(Exception e) {
			throw new DataEnteringException("Data cannot be entered to Database...");
		}
	}
	
	
	
	
	
	
/* Assigning Test to a user by his userId*/

	
	public boolean assignTest(long testId, long userId){
		try{
			String statement = "SELECT user FROM User_Test user WHERE User_Id=:pUser and Test_Id=:pTest";
			TypedQuery<User_Test> query = entityManager.createQuery(statement, User_Test.class);
			query.setParameter("pUser", userId);
			query.setParameter("pTest", testId);
			List<User_Test> user_Test = query.getResultList();
			if(!user_Test.isEmpty()) {
				throw new NoDataFoundedException("User Is already assigned to the test.");
			}
			Test test = entityManager.find(Test.class, testId);
			User user = entityManager.find(User.class, userId);
			if(test == null || user == null) {
				throw new NoDataFoundedException("User id Or Test Id Is Invalid...");
			}
				User_Test usertest = new User_Test(user, test, 0, false, 0);
				test.addUserTestDetails(usertest);
				entityManager.merge(usertest);
				return true;
			}
			catch(NoDataFoundedException exception) {
				throw new NoDataFoundedException(exception.getMessage());
			}
			catch(Exception exception) {
				throw new NoDataFoundedException("Internal Server Error...");
			}
	}
	
	
	
	
	
	
/*Get total tests of all users*/
	
	public List<User_Test> getTests() throws Exception {
		try {
			String qStr = "SELECT ut from User_Test ut";
			TypedQuery<User_Test> query = entityManager.createQuery(qStr, User_Test.class);
			List<User_Test> testsList = query.getResultList();
			if(testsList == null || testsList.size() == 0) {
				throw new NoDataFoundedException("No data Found...");
			}
			return testsList;
			}
			catch(NoDataFoundedException exception) {
				throw new NoDataFoundedException("No Data Available in database...");
			}
			catch(Exception exception) {
				throw new DataMismatchExcpetion("Internal server error!");
			}
	}
	
	
	
	
	

	
	
	
	
	
	/*
	 * Top performers
	 * 
	 * 
	 */
	
	
	
    /*Getting the top performer by considering all tests*/

	
	
	public List<User_Test> topPerformers() throws Exception {
		try {
			String statement = "SELECT user FROM User_Test user WHERE isAttempted=1";
			TypedQuery<User_Test> query = entityManager.createQuery(statement, User_Test.class);
			List<User_Test> user_Test = query.getResultList();
			if(user_Test.size() == 0) {
				throw new NoDataFoundedException("No Test Assigned to particular User...");
			}
			
			Comparator<User_Test> compareByMarks = Comparator.comparingLong(ob -> ob.getMarksScored());
			user_Test.sort(compareByMarks.reversed());
			
			if(user_Test.size()<=10) {
				return user_Test;
			}
				
			return user_Test.subList(0, 10);
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage());
		}
	}
	
	
	
	
	
/*Getting the top performer by considering a particular test*/

	
	public List<User> topPerformersOfTest(long testId) throws Exception {
		try {
			String statement = "SELECT u FROM User_Test u WHERE isAttempted=1 AND Test_Id="+testId;
			TypedQuery<User_Test> query = entityManager.createQuery(statement, User_Test.class);
			List<User_Test> userTests = query.getResultList();
			Comparator<User_Test> compareByMarks = Comparator.comparingLong(ob -> ob.getMarksScored());
			userTests.sort(compareByMarks.reversed());

			if(userTests.size() == 0) {
				throw new NoDataFoundedException("No one participated in that particular test");
			}
			ArrayList<User> users=new ArrayList<User>();
			for(int i=0;i<Math.min(10,userTests.size());i++)
			{
				users.add(userTests.get(i).getUser());
			}
			return users;
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage());
		}
	}
	
	
	/*Getting the top 10 performers average results*/

	
	public List<User_Test> topPerformersAvg() throws Exception {
		try {
			String statement = "SELECT user FROM User_Test user WHERE isAttempted=1";
			TypedQuery<User_Test> query = entityManager.createQuery(statement, User_Test.class);
			List<User_Test> user_Test = query.getResultList();
			if(user_Test.size() == 0) {
				throw new NoDataFoundedException("No Test Assigned to particular User...");
			}
			
			Comparator<User_Test> compareByMarks = Comparator.comparingLong(ob -> ob.getMarksScored());
			user_Test.sort(compareByMarks.reversed());
			
			if(user_Test.size()<4) {
				return user_Test;
			}
			return user_Test;
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage());
		}
	}

	
	

	
	
	 /*Getting the total questions per category from all questions in the database*/


		public HashMap<String, Long> questionsCategory() throws NoDataFoundedException {
			try {
				String statement = "SELECT category FROM Category category";
				TypedQuery<Category> query = entityManager.createQuery(statement, Category.class);
				List<Category> category = query.getResultList();
				if(category.size() == 0) {
					throw new NoDataFoundedException("No Details is founded in Category table...");
				}
				HashMap<String, Long> data = new HashMap<String, Long>();
				
				category.forEach(e-> {
					
					long number = (Long)e.getAllQuestion().stream().count();
					data.put(e.getName(), number);
					
				});
						
				return data;
			}
			catch(NoDataFoundedException exception) {
				throw new NoDataFoundedException(exception.getMessage());
			}		
		}
		
		
		
		
	
	

	public long getTotalUsers() throws CannotRetrieveDataException {
		try {
			String statement = "SELECT COUNT(user.userId) FROM User user WHERE isAdmin=0";
			TypedQuery<Long> query = entityManager.createQuery(statement, Long.class);
			long numberOfUsers = (Long)query.getSingleResult();
				
			return numberOfUsers;
		}
		catch(Exception exception) {
			throw new CannotRetrieveDataException(exception.getMessage());
		}
	}
	
	
	
	

	public long getTotalTests() throws CannotRetrieveDataException {
		try {
			String statement = "SELECT COUNT(test.test_Id) FROM Test test";
			TypedQuery<Long> query = entityManager.createQuery(statement, Long.class);
			long numberOfTest = (Long)query.getSingleResult();
				
			return numberOfTest;
		}
		catch(Exception exception) {
			throw new CannotRetrieveDataException(exception.getMessage());
		}
	}
	
	
	
	
	

	public long getTotalQuestions() throws CannotRetrieveDataException {
		try {
			String statement = "Select count(question.questionId) from Question question";
			TypedQuery<Long> query = entityManager.createQuery(statement, Long.class);
			long numberOfQuestions = (Long)query.getSingleResult();
				
			return numberOfQuestions;
		}
		catch(Exception exception) {
			throw new CannotRetrieveDataException(exception.getMessage());
		}
	}
	
	
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
//	
//	public boolean deassignTest(long testId, long userId) throws Exception{
//		try{
//			String statement = "SELECT user FROM User_Test user WHERE User_Id=:pUser and Test_Id=:pTest";
//			TypedQuery<User_Test> query = entityManager.createQuery(statement, User_Test.class);
//			query.setParameter("pUser", userId);
//			query.setParameter("pTest", testId);
//			if(query.getResultList().isEmpty()) {
//				throw new NoDataFoundedException("User Is de-assigned already in that particular test already...");
//			}
//			User_Test user_Test = query.getResultList().get(0);
//
//			Test test = entityManager.find(Test.class, testId);
//			User user = entityManager.find(User.class, userId);
//			if(test == null || user == null) {
//				throw new NoDataFoundedException("User id Or Test Id Is Invalid...");
//			}
//				
//				
//				return true;
//			}
//			catch(NoDataFoundedException exception) {
//				throw new NoDataFoundedException(exception.getMessage());
//			}
//			catch(Exception exception) {
//				throw new Exception("Internal Server Error...");
//			}
//	}
	
}
