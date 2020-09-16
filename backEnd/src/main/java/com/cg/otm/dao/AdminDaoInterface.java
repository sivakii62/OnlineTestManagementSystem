package com.cg.otm.dao;

import java.util.HashMap;
import java.util.List;

import com.cg.otm.entities.CategoryResult;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.exceptions.CannotRetrieveDataException;
import com.cg.otm.exceptions.NoDataFoundedException;

public interface AdminDaoInterface {

	
	public List<User> getAllUsers() throws CannotRetrieveDataException ;
	public List<Test> getAllTests() throws CannotRetrieveDataException ;
	public List<User_Test> allExams() throws Exception;
	public User_Test getUserTest(long userTestId) throws Exception;
	public boolean setScore(User_Test userTest) throws Exception;
	public void setCategoryResult(CategoryResult categoryResult) throws Exception;
	public boolean assignTest(long testId, long userId) throws Exception;
	public List<User_Test> getTests() throws Exception ;
	public List<User_Test> topPerformers() throws Exception ;
	public List<User> topPerformersOfTest(long testId) throws Exception ;
	public List<User_Test> topPerformersAvg() throws Exception ;
	public HashMap<String, Long> questionsCategory() throws NoDataFoundedException ;
	public long getTotalUsers() throws CannotRetrieveDataException ;
	public long getTotalTests() throws CannotRetrieveDataException ;
	public long getTotalQuestions() throws CannotRetrieveDataException ;
	}
	

