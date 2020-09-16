package com.cg.otm.service;

import java.util.HashMap;
import java.util.List;

import com.cg.otm.entities.CategoryResult;
import com.cg.otm.entities.Test;
import com.cg.otm.entities.User;
import com.cg.otm.entities.User_Test;
import com.cg.otm.exceptions.CannotRetrieveDataException;
import com.cg.otm.exceptions.NoDataFoundedException;

public interface AdminServiceInterface {
	
	
	public List<User> getAllUsers() throws CannotRetrieveDataException;
	public List<Test> getAllTests() throws CannotRetrieveDataException;
	public Long calculateScoreService(Long userTestId) throws Exception;
	public List<CategoryResult> categoryScore(Long userTestId) throws Exception;
	public boolean assignTest(long testId, long userId) throws Exception ;
	public List<User_Test> getTests() throws Exception ;
	public List<User_Test> topPerformers() throws Exception ;
	public List<User> topPerformersOfTest(long testId) throws Exception ;
	public HashMap<Long, Avg> topPerformersAvg() throws Exception ;
	public HashMap<String, Long> questionsCategory() throws NoDataFoundedException ;
	public long getTotalUsers() throws CannotRetrieveDataException ;
	public long getTotalTests() throws CannotRetrieveDataException ;
	public long getTotalQuestions() throws CannotRetrieveDataException ;
}
