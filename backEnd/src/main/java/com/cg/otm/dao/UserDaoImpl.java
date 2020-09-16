package com.cg.otm.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cg.otm.entities.CategoryResult;
import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.exceptions.DataMismatchExcpetion;
import com.cg.otm.exceptions.NoDataFoundedException;
import com.cg.otm.exceptions.QuestionsNotFoundException;



@Repository
@Transactional
public class UserDaoImpl implements UserDao{

	@PersistenceContext
	protected EntityManager entityManager;
	
	
	/*Method: getUserByName
	 * Description:Used for getting the user details based onn the username
	 * @param username: username
	 * @return User: details of the user
	 */	
	
	@Override
	public User getUserByName(String username) {
		String str = "select user from User user where user.userName = :username";
  		TypedQuery<User> query = entityManager.createQuery(str,User.class);
  		query.setParameter("username",username);
  		User user = query.getResultList().get(0);
		return user;
	}
	
	
	/*Method: findById
	 * Description:Used to find the user based on the id .
	 * @param id :userId of the user
	 * @return User:it returns the user details
	 */
	@Override
	public User findById(Integer id) {
		return entityManager.find(User.class, id);
	}
	
	
	/*Method: signUp
	 * Description:Used for the signup of the new user
	 * @param cust :details of the user
	 * @return void
	 */
	@Override
	public void signUp(User customer) {
		customer.setAdmin(false);
		entityManager.persist(customer);		
	}

	
	/*Method: getAllUsers
	 * Description:Used for getting all the users
	 * @return List<User>: list if the users
	 */
	@Override
	public List<User> getAllUsers() {
		String str = "select user from User user where user.isAdmin=0";
		TypedQuery<User> query=entityManager.createQuery(str, User.class);
		
		return query.getResultList();
		
	}
	
	
	
	/*Method: SignUpEmailValidate
	 * Description:check for validate of email
	 * @param email: email registering for user
	 * @return int: returns if email is valid
	 */
    public int SignUpEmailValidate(String email) 
     {
      	List<User> passList = new ArrayList<User>();
      	String str = "select user from User user where user.emailId = :emailid";
  		TypedQuery<User> query = entityManager.createQuery(str,User.class);
  		query.setParameter("emailid",email);
  		passList = query.getResultList();
  		if(passList.size()>0) 
  			return 1;
  		else 
  			return 0;
      }

    
    
	/*Method: userExistsOrNot
	 * Description:check for validate of username
	 * @param userName: username registering for user
	 * @return int: returns if username is valid
	 */
     public int userExistsOrNot(String userName) {
     	List<User> passList = new ArrayList<User>();
     	String str = "select user from User user where user.userName = :username";
 		TypedQuery<User> query = entityManager.createQuery(str,User.class);
 		query.setParameter("username",userName);
 		passList = query.getResultList();
 		if(passList.size()>0) {
 			return 1;
 		}
 		else 
 			return 0;
 	}

	public void createAdmin(User admin) {
		admin.setAdmin(true);;
		entityManager.persist(admin);
	}
     
		@Override
	public List<User> getallAdmins() {
	
		String str="select admin from User admin where admin.isAdmin=1";
		TypedQuery<User> query=entityManager.createQuery(str, User.class);
	
		return query.getResultList();
	}

	
	
	/*Method: getUserId
	 * Description:Used for gettinng the user id from username
	 * @param username :username of the user
	 * @return Long: userId
	 */
	@Override
	public Long getUserId(String username) {
			String str = "select user from User user where user.userName = :username";
			TypedQuery<User> query = entityManager.createQuery(str,User.class);
			query.setParameter("username",username);
			User u = query.getResultList().get(0);
		    return u.getUserId();
	}
	
	/*Method: getResult
	 * Description:used for getting the result of the user
	 * @param userId :id of the user
	 * @return List<User_Test> : all the test details the user have  taken
	 * @throws Exception :it is raised when no test is taken
	 */
	public List<User_Test> getResult(String username) throws Exception{
	long userId=getUserId(username);
		try {
		User user = entityManager.find(User.class,userId);
		String qStr = "SELECT ut from User_Test ut where ut.user=:user and ut.isAttempted = 1";
		TypedQuery<User_Test> query = entityManager.createQuery(qStr, User_Test.class);
		query.setParameter("user", user);
		List<User_Test> list=query.getResultList();
		if(list == null || list.size() == 0) {
			throw new NoDataFoundedException("No data Found...");
		}
		return list;
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException("No Data Available in database...");
		}
		catch(Exception exception) {
			throw new DataMismatchExcpetion("Internal server error!");
		}
	}

	/*Method: getCategoryResult
	 * Description:Used for getting the category result of the user
	 * @param userTestId :test id of the user
	 * @return List<CategoryResult>: result of the test the user have taken
	 * @throws Exception :it is raised when no test is taken
	 */
	public List<CategoryResult> getCategoryResult(Long userTestId) throws Exception{
	
		try {
			
		User_Test userTest = entityManager.find(User_Test.class,userTestId);
		String qStr = "SELECT cr from CategoryResult cr where cr.userTest=:userTest";
		TypedQuery<CategoryResult> query = entityManager.createQuery(qStr, CategoryResult.class);
		query.setParameter("userTest", userTest);
		List<CategoryResult> list=query.getResultList();
		if(list == null || list.size() == 0)
			throw new NoDataFoundedException("No data Founded...");
		return list;
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException("No Data Available in database...");
		}
		catch(Exception exception) {
			throw new DataMismatchExcpetion("Internal server error!");
		}
	}


	public List<Question> getQuestions(Long testId) throws Exception{
		try {
		Test test = entityManager.find(Test.class, testId);
		
		List<Question> questionsList =  test.getAllQuestion();
		
		if(questionsList == null)
			throw new QuestionsNotFoundException();
		
		return questionsList; 
		}
		catch(QuestionsNotFoundException exception){
			throw new QuestionsNotFoundException("No questions available for this test");
		}
	}
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * GetResult
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	
	public Integer getAssignedTest(long userId) throws Exception {
		return getAllTestAssign(userId).size();
		
	}

	
	
	/*Method: getUpcominngTest
	 * Description:Used for getting the count of the upcoming tests for the user
	 * @param userId :id of the user
	 * @return Integer: count of the upcoming tests
	 * @throws Exception :it is raised when no upcominng tests
	 */
	@Override
	public Integer getUpcomingTest(long userId) throws Exception {
		try {
			List<Test> testList = getAllTestAssign(userId);
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			List<Test> updatedTestList = new ArrayList<>();
			for(Test test: testList) {
				int number = test.getEndDate().compareTo(timeStamp);
				if(number > 0) {
					updatedTestList.add(test);
				}
			
			}
			if(updatedTestList.size() == 0) {
				throw new NoDataFoundedException("All test are passed");
			}
			return updatedTestList.size();
		}
		catch(DataMismatchExcpetion exception) {
			throw new DataMismatchExcpetion(exception.getMessage());
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage()); 
		}
		catch(Exception exception) {
			throw new DataMismatchExcpetion("Internal server error!");
		}
	}

	
	/*Method: getActiveTest
	 * Description:Used for getting the count of the active tests for the user
	 * @param userId :id of the user
	 * @return Integer: count of the active tests
	 * @throws Exception :it is raised when no active tests
	 */
	@Override
	public Integer getActiveTest(long userId) throws Exception {
		try {
			
			List<Test> testList = getAllTestAssign(userId);
			int count = 0;
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			for(Test test: testList) {
				int startNumber = test.getStartDate().compareTo(timeStamp);
				int endNumber = test.getEndDate().compareTo(timeStamp);
				if(startNumber > 0 && endNumber < 0) {
					count++;
				}
			}
			if(count > 0) {
				return count;
			}
			else {
				return 0;
			}
		}
		catch(DataMismatchExcpetion exception) {
			throw new DataMismatchExcpetion(exception.getMessage());
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage()); 
		}
		catch(Exception Exception) {
			throw new java.lang.Exception("Internal server error!");
		}
	}

	public boolean isUserExist(long userId) {
		User user = entityManager.find(User.class, userId);
		if(user != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean assignTest(long testId, long userId) throws Exception{
		try{
			String statement = "SELECT user FROM User_Test user WHERE User_Id=:pUser and Test_Id=:pTest";
			TypedQuery<User_Test> query = entityManager.createQuery(statement, User_Test.class);
			query.setParameter("pUser", userId);
			query.setParameter("pTest", testId);
			List<User_Test> user_Test = query.getResultList();
			if(!user_Test.isEmpty()) {
				throw new NoDataFoundedException("User Is assigned in that perticular test already...");
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
				throw new Exception("Internal Server Error...");
			}
	}
	
	
	public List<Test> getAllTestAssign(long userId) throws Exception{
		try {
			if(!isUserExist(userId)){
				 throw new DataMismatchExcpetion("No such User Exist...");
			}
			String statement = "SELECT user FROM User_Test user WHERE User_Id=:pUser";
			TypedQuery<User_Test> query = entityManager.createQuery(statement, User_Test.class);
			query.setParameter("pUser", userId);
			List<User_Test> user_Test = query.getResultList();
			if(user_Test.size() == 0) {
				throw new NoDataFoundedException("No Test Assigned to particular User...");
			}
			List<Test> testList = new ArrayList<>();
			user_Test.forEach(t->System.out.println(t.getTestId().getTest_Id()));
			for(User_Test val: user_Test) {
				Test test = entityManager.find(Test.class, val.getTestId().getTest_Id());
				testList.add(test);
			}
			return testList;
			
		}
		catch(DataMismatchExcpetion exception) {
			throw new DataMismatchExcpetion(exception.getMessage());
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage());
		}
		catch(Exception exception) {
			throw new DataMismatchExcpetion("Internal server error!");
		}
		
		
	}

	/*Method: Validate
	 * Description:Used for validatinng the username and password for loging in
	 * @param id :id of the user
	 * @param password:logi password of the user
	 */
	public int validate(String id, String password) {
		
			String q1="SELECT b.userName FROM User b";
			TypedQuery<String> q2=entityManager.createQuery(q1,String.class);
			List<String> l1=q2.getResultList();
			if(l1.contains(id))
			{
				
				String s1 = "" + id;
				String qStr = "SELECT a.userPassword FROM User a  where a.userName=:num";
				TypedQuery<String> query = entityManager.createQuery(qStr, String.class);
				query.setParameter("num", s1);
				String num1 = query.getSingleResult();
			      User l=getUserByName(id);
				if (num1.equals(password)&&l.isAdmin()==false) {
					return 1; 
				}
				else if(num1.equals(password)&&l.isAdmin()==true)
				{
					return 2;
				}
				else {
					return 0; 
				}
			}
			else
				return 0;
		
	}

public boolean change(String username,String password,String newPassword)
{
	int s=validate(username,password);
	if(s==0)
	{
		return false;
	}
	else
	{
		User u=getUserByName(username);
		u.setUserPassword(newPassword);
		return true;
	}

	/*
	 * This method is used to get all test list assigned to particular user.
	 * @param userId This is parameter of long type of getAllTestAssignToPerticularUser method.
	 * @return List<Test> this return List of test if data available otherwise throw exception.
	 */
}
	public List<Test> getAllTestAssignToPerticularUser(String username) throws Exception {
		long userId=getUserId(username);
		try {
			if(!isUserExist(userId)){
				 throw new DataMismatchExcpetion("No such User Exist...");
			}
			String statement = "SELECT user FROM User_Test user WHERE User_Id=:pUser";
			TypedQuery<User_Test> query = entityManager.createQuery(statement, User_Test.class);
			query.setParameter("pUser", userId);
			List<User_Test> user_Test = query.getResultList();
			if(user_Test.size() == 0) {
				throw new NoDataFoundedException("No Test Assigned to particular User...");
			}
			List<Test> testList = new ArrayList<>();
			user_Test.forEach(t->System.out.println(t.getTestId().getTest_Id()));
			for(User_Test val: user_Test) {
				Test test = entityManager.find(Test.class, val.getTestId().getTest_Id());
				testList.add(test);
			}
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			for(Test test: testList) {
				int number = test.getEndDate().compareTo(timeStamp);
				int secondComp = test.getStartDate().compareTo(timeStamp);
				int lastComp = timeStamp.compareTo(test.getEndDate());
				
				if(number > 0) {
					test.setTestStatus(1);
				}
				else {
					test.setTestStatus(-1);
				}
				if(secondComp <= 0 && lastComp < 0) {
					test.setTestStatus(0);
					
				}
				
			}
			return testList;
			
		}
		catch(DataMismatchExcpetion exception) {
			throw new DataMismatchExcpetion(exception.getMessage());
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage());
		}
		catch(Exception exception) {
			throw new Exception("Internal server error!");
		}
		
		
	}
	public List<Test> getAllTest() throws Exception{
		
		try {
			String statement = "SELECT test FROM Test test";
			TypedQuery<Test> query = entityManager.createQuery(statement, Test.class);
			List<Test> testList = query.getResultList();
			if(testList.size() == 0) {
				throw new NoDataFoundedException("No Test Is Held Yet!... Wait for Completion of test!!");
			}
			Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
			for(Test test: testList) {
				int number = test.getEndDate().compareTo(timeStamp);
				int secondComp = test.getStartDate().compareTo(timeStamp);
				int lastComp = timeStamp.compareTo(test.getEndDate());
				
				if(number > 0) {
					test.setTestStatus(1);
				}
				else {
					test.setTestStatus(-1);
				}
				if(secondComp <= 0 && lastComp < 0) {
					test.setTestStatus(0);
				}
				
			}
			return testList;
		}
		catch(NoDataFoundedException exception) {
			throw new NoDataFoundedException(exception.getMessage()); 
		}
		catch(Exception exception) {
			throw new DataMismatchExcpetion("Internal server error!");
		}
}
	public boolean forgotPassword(String username,String password,String email)
	{
		String q1="SELECT b.userName FROM User b";
		TypedQuery<String> q2=entityManager.createQuery(q1,String.class);
		List<String> l1=q2.getResultList();
		
		User u=null;
		if(l1.contains(username))
		{
			
		u=getUserByName(username);
		u.setUserPassword(password);
		entityManager.merge(u);
		return true;
	
		}
		else
			return false;
	}
}
