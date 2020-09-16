package com.cg.otm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.cg.otm.service.AttemptTestServiceImpl;


public class AttemptTestMockito {

	AttemptTestServiceImpl attemptTestService = Mockito.mock(AttemptTestServiceImpl.class);
	
	
	
	@Test
	void testgetAllTestAssignToParticularUser() throws Exception {
		
		when(attemptTestService.getAllTestAssignToParticularUser("siva")).thenReturn(null);
		assertEquals(null, attemptTestService.getAllTestAssignToParticularUser("siva"));
	}
	
	
	@Test
	void testgetAllUpcomingTest() throws Exception {
		
		when(attemptTestService.getAllTest()).thenReturn(null);
		assertEquals(null, attemptTestService.getAllTest());
		
	}
	

}

