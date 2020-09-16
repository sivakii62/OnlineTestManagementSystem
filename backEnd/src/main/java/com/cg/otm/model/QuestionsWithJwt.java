package com.cg.otm.model;

import java.util.List;

import com.cg.otm.entities.Question;

public class QuestionsWithJwt {

	private List<Question> questions;
	private String jwt;
	
	
	public QuestionsWithJwt() {
	
	}
	
	public QuestionsWithJwt(List<Question> questions, String jwt) {
		super();
		this.questions = questions;
		this.jwt = jwt;
	}
	
	
	public List<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public String getJwt() {
		return jwt;
	}
	
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
	
}
