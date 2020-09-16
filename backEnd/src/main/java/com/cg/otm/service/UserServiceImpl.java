package com.cg.otm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.otm.entities.Test;
import com.cg.otm.dao.UserDaoImpl;
import com.cg.otm.entities.CategoryResult;
import com.cg.otm.entities.Question;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.exceptions.EmailExistsException;
import com.cg.otm.exceptions.UserNameExistsException;
   
@Service
public class UserServiceImpl  implements UserService{
	
	@Autowired
	UserDaoImpl dao;
	Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	/*Method: findById
	 * Description:Used to find the user based on the id .
	 * @param id :userId of the user
	 * @return User:it returns the user details
	 */
	@Override
	public User findById(Integer id) {

		User user=dao.findById(id);
		return user;
	}

	/*Method: signUp
	 * Description:Used for the signup of the new user
	 * @param cust :details of the user
	 * @return void
	 * @throws EmailExistsException :it is raised when the email given by user already exists
	 * @throws UserNameExistsException :it is raised when the username given by user already exists
	 */
	@Override
	public void signUp(User cust) throws EmailExistsException,UserNameExistsException {
		int userNameStatus=dao.userExistsOrNot(cust.getUserName());
		int emailStatus=dao.SignUpEmailValidate(cust.getEmailId());
		
	    if(emailStatus==1)
		{
	    	logger.error("Email already used ,try with different Email Id....");
			throw new EmailExistsException("Email already used ,try with different Email Id");
		}
	    	
		else if(userNameStatus==1) 
		{
			logger.error("UserName already taken ,try different UserName.....");
			throw new UserNameExistsException("UserName already taken ,try different UserName"); 
		}
	    logger.info("signup successful");
		dao.signUp(cust);
		
		
	}
	/*Method: getUserByName
	 * Description:Used for getting the user details based onn the username
	 * @param username: username
	 * @return User: details of the user
	 */	
	@Override
	public User getUserByName(String username) {
		logger.info("user found successfully....");
		return dao.getUserByName(username);
	}
	
	
	/*Method: getAllUsers
	 * Description:Used for getting all the users
	 * @return List<User>: list if the users
	 */
	@Override
	public List<User> getAllUsers() {
		logger.info("users found successfully....");
		return dao.getAllUsers();
	}

	
	public void createAdmin(User admin) {
		dao.createAdmin(admin);
		
		
		
	}

	@Override
	public List<User> getAllAdmins() {
		logger.info("admins found successfully....");
		return dao.getallAdmins();
	}

	/*Method: getUserId
	 * Description:Used for gettinng the user id from username
	 * @param username :username of the user
	 * @return Long: userId
	 */
	@Override
	public Long getUserId(String username) {
		logger.info("userId found successfully....");
		return  dao.getUserId(username);
	}
	
	/*Method: getResult
	 * Description:used for getting the result of the user
	 * @param userId :id of the user
	 * @return List<User_Test> : all the test details the user have  taken
	 * @throws Exception :it is raised when no test is taken
	 */
	
	@Override
	public List<User_Test> getResult(String userId) throws Exception {
		logger.info("user tests found successfully....");
		return dao.getResult(userId);
	}

	
	/*Method: getCategoryResult
	 * Description:Used for getting the category result of the user
	 * @param userTestId :test id of the user
	 * @return List<CategoryResult>: result of the test the user have taken
	 * @throws Exception :it is raised when no test is taken
	 */
	@Override
	public List<CategoryResult> getCategoryResult(Long userTestId) throws Exception{
		logger.info("user result found successfully....");
		return dao.getCategoryResult(userTestId);
	}
	
	public List<Question> getQuestions(Long testId) throws Exception{
		return dao.getQuestions(testId);
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
	
	
	
	
	
	public boolean isUserExist(long userId) {
		return dao.isUserExist(userId);
	}
	/*Method: getUpcominngTest
	 * Description:Used for getting the count of the upcoming tests for the user
	 * @param userId :id of the user
	 * @return Integer: count of the upcoming tests
	 * @throws Exception :it is raised when no upcominng tests
	 */
	@Override
	public Integer getUpcomingTest(long userId) throws Exception {
		logger.info("users upcominng tests count found successfully....");
		return dao.getUpcomingTest(userId);
	}
	/*Method: getActiveTest
	 * Description:Used for getting the count of the active tests for the user
	 * @param userId :id of the user
	 * @return Integer: count of the active tests
	 * @throws Exception :it is raised when no active tests
	 */
	@Override
	public Integer getActiveTest(long userId) throws Exception {
		logger.info("users active tests count found successfully....");
		return dao.getActiveTest(userId);
	}

	public Integer getAssignedTest(long userId) throws Exception {
		return dao.getAssignedTest(userId);
	}

	public boolean assignTest(long testId, long userId) throws Exception {
		return dao.assignTest(testId, userId);
	}

	/*Method: Validate
	 * Description:Used for validatinng the username and password for loging in
	 * @param id :id of the user
	 * @param password:logi password of the user
	 */
	@Override
	public int validate(String id, String password) {
		
					
			 int checkingLogin= dao.validate(id,password);
				logger.info("password annd username validated....");
			 return checkingLogin;
		
	}
	
	
	public  boolean ChangePassword(String username,String password,String newPassword)
	{
		return dao.change(username,password,newPassword);
	}
	
	
	
	public List<Test> getAllTestAssignToPerticularUser(String userId) throws Exception{
		logger.info("getAllTestAssignToPerticularUser service method accessed.");
		return dao.getAllTestAssignToPerticularUser(userId);
	}
	
	public List<Test> getAllTest() throws Exception{
		logger.info("getAllUpcomingTest service method accessed.");
		return dao.getAllTest();
	}
	
	public boolean forgotPassword(String username,String password,String email)
	{
		return dao.forgotPassword(username,password,email);
	}

	
	
}