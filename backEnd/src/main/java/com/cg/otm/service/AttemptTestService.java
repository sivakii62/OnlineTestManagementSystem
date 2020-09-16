package com.cg.otm.service;

import java.util.List;


import com.cg.otm.entities.Question;
import com.cg.otm.entities.Test;

public interface AttemptTestService {
	
	public List<Test> getAllTestAssignToParticularUser(long userId) throws Exception;
	public List<Test> getAllTest() throws Exception;
	public Test getActiveTest(long userId, long testId) throws Exception;
	public List<Question> getAllQuestion(long userId, long testId) throws Exception;
	public boolean submitTest(List<Integer> answer, long testId, long userId) throws Exception;
	List<Test> getAllTestAssignToParticularUser(String userName) throws Exception;
	

}
