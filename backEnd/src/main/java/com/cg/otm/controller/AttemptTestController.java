package com.cg.otm.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;
import com.cg.otm.model.QuestionsWithJwt;
//import com.cg.otm.security.JwtUtil;
import com.cg.otm.service.AttemptTestServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/Start")
public class AttemptTestController {

	@Autowired
	private AttemptTestServiceImpl service;
	
//	@Autowired
//	JwtUtil jwtUtil;

	
	/*
	 * Logger used to Record unusual circumstances or error that may be happening in
	 * the program. also use to get info what is going in the application.
	 */
	Logger logger = LoggerFactory.getLogger(AttemptTestServiceImpl.class);
	

	/*
	 * getTestByUserId Method takes userId as argument and return list of All test
	 * assigned to that particular user.
	 */
	@GetMapping("getAllTestByUserId")
	public ResponseEntity<List<Test>> getTestByUserName(@RequestParam String userName) throws Exception {
		logger.trace("GetTestByUserId Method Accessed..."); // Default level is Info and trace is not upto the Info
															// level so we have to set the property in
															// application.context
//		String userName =  jwtUtil.extractUsername(request.getHeader("Authorization").substring(7));
		return new ResponseEntity<List<Test>>(service.getAllTestAssignToParticularUser(userName), HttpStatus.OK);
	}

	/*
	 * getUpcomingTest Method takes userId as argument and return list of all
	 * upcoming test details.
	 */
	@GetMapping("getAllTest")
	public ResponseEntity<List<Test>> getAllTest() throws Exception {
		logger.trace("getAllTest Method Accessed...");
		return new ResponseEntity<List<Test>>(service.getAllTest(), HttpStatus.OK);
	}

	/*
	 * getActiveTest Method takes userId as Argument and return only test which is
	 * currently active.
	 */
	@GetMapping("getTestByUserId")
	public ResponseEntity<Test> getActiveTest(@RequestParam String userName, @RequestParam("testId") long testId)
			throws Exception {
		logger.trace("getActiveTest Method Accessed...");
//		String userName =  jwtUtil.extractUsername(request.getHeader("Authorization").substring(7));
		return new ResponseEntity<Test>(service.getActiveTest(userName, testId), HttpStatus.OK);
	}

	/*
	 * getAllQuestion Method takes testId as argument and return List of question
	 * assigned to particular test.
	 */
	@GetMapping("getAllQuestion")
	public ResponseEntity<QuestionsWithJwt> getAllQuestion(@RequestParam String userName,
			@RequestParam("testId") long testId) throws Exception {
		logger.trace("getAllQuestion Method Accessed...");
//		String userName =  jwtUtil.extractUsername(request.getHeader("Authorization").substring(7));
		return new ResponseEntity<QuestionsWithJwt>(service.getAllQuestion(userName, testId), HttpStatus.OK);
	}

	/*
	 * setUserAnswers takes GetAnswer class object as an input and returns the
	 * response as a message.
	 */
	@PostMapping("setTestAnswer")
	public ResponseEntity<Object> setUserAnswers(@RequestBody GetAnswer getAnswer) throws Exception {
		logger.trace("setUserAnswers Method Accessed...");
//		String userName =  jwtUtil.extractUsername(request.getHeader("Authorization").substring(7));
		service.submitTest(getAnswer.answer, getAnswer.testId, getAnswer.userName);
		return new ResponseEntity<Object>("User Answer Updated Successfully", HttpStatus.OK);
	}

}

//classes required to take more than a parameter from front end.

class GetAnswer {
	public List<Integer> answer;
	public String userName;
	public long testId;
}

