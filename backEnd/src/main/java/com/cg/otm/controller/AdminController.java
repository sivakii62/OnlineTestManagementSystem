package com.cg.otm.controller;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.otm.entities.CategoryResult;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.exceptions.CannotRetrieveDataException;
import com.cg.otm.exceptions.NoDataFoundedException;
import com.cg.otm.service.AdminServiceImpl;

@SpringBootApplication
@RestController
@CrossOrigin("*")
@RequestMapping("/Admin")
public class AdminController {

	
	@Autowired
	AdminServiceImpl service;
	
	
	
	/*Get All Users To view or edit*/
	
	
	@GetMapping("getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() throws CannotRetrieveDataException{
		return new ResponseEntity<List<User>>(service.getAllUsers(), HttpStatus.OK);
	}
	
	

	/*Get All Tests To view or edit*/
	
	
	@GetMapping("getAllTests")
	public ResponseEntity<List<Test>> getAllTests() throws CannotRetrieveDataException{
		return new ResponseEntity<List<Test>>(service.getAllTests(), HttpStatus.OK);
	}
	
	
	
    /*Getting all exams of all users*/
	@GetMapping("userTests")
	public ResponseEntity<List<User_Test>> allExams() throws Exception{
		return new ResponseEntity<List<User_Test>>(service.allExams(), HttpStatus.OK);
	}
	
	
	
	/*Get score of a particular user's exam from its userTestID */
	
	
	@GetMapping("getScore/{userTestId}")
	public ResponseEntity<Object> getTestScore(@PathVariable Long userTestId) throws Exception{
		return new ResponseEntity<Object>(service.calculateScoreService(userTestId), HttpStatus.OK);
	}
	
	
	
	/*Get score of a particular user's exam by category of questions from its userTestID */

	@GetMapping("getCategoryScore/{userTestId}")
	public ResponseEntity<List<CategoryResult>> getTestCategoryScore(@PathVariable Long userTestId) throws Exception{
		return new ResponseEntity<List<CategoryResult>>(service.categoryScore(userTestId), HttpStatus.OK);
	}
	
	
	

	
	/*
	 * 
	 * Assigning Test to a user by his userId*/
	
	
	
	
	@GetMapping("assignTest/{testId}/{userId}")
	public boolean assignTest(@PathVariable("testId") long testId, @PathVariable("userId") long userId) throws Exception{
	    return service.assignTest(testId,userId);
	    
	}
	
	
	
	
	/*
	 * 
	 * 
	 * Top performers
	 */
	
	
	/*Getting the top performer by considering all tests*/
	

	@GetMapping("top_performers")
	public ResponseEntity<List<User_Test>> listofTopPerformers() throws Exception{
		return new ResponseEntity<List<User_Test>>(service.topPerformers() ,HttpStatus.OK);
	}
	
	
	
	/*Getting the top performer by considering a particular test*/

	@GetMapping("top_performers/{testId}")
	public ResponseEntity<List<User>> listofTopPerformersOfTest(@PathVariable("testId") long testId) throws Exception{
		return new ResponseEntity<List<User>>(service.topPerformersOfTest(testId) ,HttpStatus.OK);
	}
	
	
	/*Getting the top 10 performers average results*/

	
	@GetMapping("top_performer_avg")
	public ResponseEntity<Object> topPerformersAvg() throws Exception{
		return new ResponseEntity<Object>(service.topPerformersAvg(), HttpStatus.OK);
	}
	

	
	
   /*Getting the total questions per category from all questions in the database*/
	
	@GetMapping("questions_category")
	public ResponseEntity<HashMap<String, Long>> questionsCategory() throws NoDataFoundedException{
		return new ResponseEntity<HashMap<String, Long>>(service.questionsCategory(), HttpStatus.OK);
	}
	
	
	/*Get count of user without loading whole users*/
	@GetMapping("user_count")
	public long getTotalUsers() throws CannotRetrieveDataException {
		return service.getTotalUsers();
	}
	
	
	/*Get count of user without loading whole tests*/
	@GetMapping("totalTests")
	public long getTotalTests() throws CannotRetrieveDataException {
		return service.getTotalTests();
	}

	/*Get count of user without loading whole Questions*/
	@GetMapping("totalQuestions")
	public long getTotalQuestions() throws CannotRetrieveDataException {
		return service.getTotalQuestions();
	}

	
	@GetMapping("getAllUserTests")
	public List<User_Test> findAllTest() throws Exception
	{
		return service.getAllUserTests();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * Deassigning Test to a user by his userId and testId*/
	
//	
//	
//	
//	@GetMapping("deaassignTest/{testId}/{userId}")
//	public ResponseEntity<Object> deassignTest(@PathVariable("testId") long testId, @PathVariable("userId") long userId) throws Exception{
//		try {
//		service.deassignTest(testId,userId);
//	    return new ResponseEntity<Object>("User Founded... and Test deassigned", HttpStatus.OK);
//		}
//		catch(Exception exception) {
//			return new ResponseEntity<Object>("Somting Went wrong...", HttpStatus.BAD_GATEWAY);
//		}
//	    
//	}
	
	

}
