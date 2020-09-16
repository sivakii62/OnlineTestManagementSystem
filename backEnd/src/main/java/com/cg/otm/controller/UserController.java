package com.cg.otm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.security.AuthenticationRequest;
import com.cg.otm.security.AuthenticationResponse;
import com.cg.otm.security.JwtUtil;
import com.cg.otm.security.MyUserDetailsService;
import com.cg.otm.service.UserServiceImpl;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {
	
	@Autowired
	UserServiceImpl service;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	Logger logger=LoggerFactory.getLogger(UserController.class);

	

	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails,0);
		final String username = jwtTokenUtil.extractUsername(jwt);

		return ResponseEntity.ok(new AuthenticationResponse(jwt,service.getUserByName(username).isAdmin()));
	}


	/**
	 * Description : For the login of the particular user
	 * @param id  - User Id
	 * @param password  - password
	 * @return int
	 */
//	@GetMapping("/acc/pa/{id}/{password}")
//	public int validation(@PathVariable String id,@PathVariable String password) 
//	{
//		int w= service.validate(id,password);
//		logger.info("validating the details....");
//		return w;
//	}

	/**
	 * Description : For the signing up for the new user
	 * @param id  - userTable
	 * @return Boolean
	 */	
	@PostMapping(value="/signup")
	public ResponseEntity<Boolean> createCustomer(@RequestBody userTable customer)
	{
		User user = new User();
		user.setActiveTest(false);
		user.setAdmin(false);
		user.setUserName(customer.username);
		user.setEmailId(customer.email);
		user.setUserPassword(customer.password);
		
		service.signUp(user);
		logger.info("signinng up with new user details....");
		return ResponseEntity.ok(true);
	}
	
	/**
	 * Description : Fetching the details of all the users 
	 * @return 
	 */
	@GetMapping(value="/getAll")
	@ResponseBody
	public ResponseEntity<?> fetchAll()
	{
		logger.info("get all the user details....");
		return ResponseEntity.ok(service.getAllUsers());
	}
	
	
	/**
	 * Description : For getting all the admins
	 * @return 
	 */
	@GetMapping(value="/getAllAdmins")
	@ResponseBody
	public ResponseEntity<?> fetchAllAdmins()
	{
		logger.info("get  all the admin details....");
		return ResponseEntity.ok(service.getAllAdmins());
	}
	
	
	/**
	 * Description : To get the the user id of the user based on the username
	 * @param username  - Username of the user
	 * @return 
	 */
	@GetMapping(value="/getUserId/{username}")
	@ResponseBody
	public ResponseEntity<?> getUserId(@PathVariable String username){
		logger.info("find the user id by username....");
		return ResponseEntity.ok(service.getUserId(username));
	}
	
	
	
	/**
	 * Description : To get the result of a user for a test
	 * @param userId  - User Id of user
	 * @return Object
	 */
	@GetMapping("/getResult/{userId}")
	public ResponseEntity<Object> getUserResult(@PathVariable String userId) throws Exception{
		logger.info("find the user result details....");
		return new ResponseEntity<Object>(service.getResult(userId), HttpStatus.OK);
	}
	
	/**
	 * Description : To get the category result of the user for the particular test
	 * @param userTestId  - test is of the user for a particular test
	 * @return Object
	 */
	@GetMapping("/getCategoryResult/{userTestId}")
	public ResponseEntity<Object> getCategoryResult(@PathVariable Long userTestId) throws Exception{
		logger.info("find the user category result details....");
		return new ResponseEntity<Object>(service.getCategoryResult(userTestId), HttpStatus.OK);
	}
	
	/**
	 * Description : To get the questions for the test
	 * @param testId  - Id of the test
	 * @return int
	 */
	@GetMapping("/getQuestions/{testId}")
	public ResponseEntity<Object> getQuestions(@PathVariable Long testId) throws Exception{

		return new ResponseEntity<Object>(service.getQuestions(testId), HttpStatus.OK);
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
	
	
	
	
	/**
	 * Description : Get count of all the tests of the user
	 * @param userId  - User Id
	 * @return Integer
	 */	
	@GetMapping("/getCountOfAllTestByUserId/{userId}")
	public ResponseEntity<Integer> getTestByUserIdCount(@PathVariable long userId) throws Exception{
		logger.info("get the count of all the tests through user id....");
			 return new ResponseEntity<Integer>(service.getAssignedTest(userId) ,HttpStatus.OK);
	}
	
	/**
	 * Description : Get count of all the upcoming tests of the user
	 * @param userId  - User Id
	 * @return Integer
	 */
	@GetMapping("/getCountOfUpcomingTestByUserId/{userId}")
	public ResponseEntity<Integer> getUpcomingTest(@PathVariable long userId) throws Exception{
		logger.info("get the count of all the upcoming tests through user id....");
		 
		return new ResponseEntity<Integer>(service.getUpcomingTest(userId), HttpStatus.OK);
	}
	
	/**
	 * Description : Get count of all the active tests of the user
	 * @param userId  - User Id
	 * @return Integer
	 */
	@GetMapping("/getCountActiveTestByUserId/{userId}")
	public ResponseEntity<Integer> getActiveTest(@PathVariable long userId) throws Exception{
		logger.info("get the cout of all the active tests through user id....");
		 
		return new ResponseEntity<Integer>(service.getActiveTest(userId), HttpStatus.OK);
	}
	
	@PutMapping("/changePassword/{username}/{password}/{newPassword}")
	public boolean changePassword(@PathVariable String username,@PathVariable String password,@PathVariable String newPassword  )
	{
		return service.ChangePassword(username,password,newPassword);
	}
	
	
//	@RequestMapping({"/hello"})
//	
//	public String hello(HttpServletRequest request){
//	final String token = request.getHeader("Authorization");			
//	final String username = jwtTokenUtil.extractUsername(token.substring(7));
//	
//
//		return username;
//	}
	@GetMapping("/getAllTestByUserId/{userId}")
	public ResponseEntity<List<Test>> getTestByUserId(@PathVariable String userId) throws Exception{
		System.out.println("dhfghudgfudhufhdufuedfjdkjuhgfdcfyuiuygtfdertyuioiutdtyukjgfcgjklkhcxgjk345670uhhcfe3w4678yuhjhvgcdre45r678uihj");
		     logger.trace("GetTestByUserId Method Accessed...");    						// Default level is Info and trace is not upto the Info level so we have to set the property in application.context 
			 return new ResponseEntity<List<Test>>(service.getAllTestAssignToPerticularUser(userId) ,HttpStatus.OK);
	}
	@GetMapping("getAllTest")
	public ResponseEntity<List<Test>> getAllTest() throws Exception{
		logger.trace("getAllTest Method Accessed...");   
		return new ResponseEntity<List<Test>>(service.getAllTest(), HttpStatus.OK);
	}
	
	@PutMapping("/forgot")
	public ResponseEntity<Boolean> forgotPassword(@RequestBody userTable customer)
	{
		
		service.forgotPassword(customer.username,customer.password,customer.email);
		logger.info("signinng up with new user details....");
		return ResponseEntity.ok(true);
	}
	
}
class userTable{
	public String username;
	public String email;
	public String password;
}
class forgot
{
	public String username;
	public String password;
}
