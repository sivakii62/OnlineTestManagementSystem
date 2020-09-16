package com.cg.otm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class DefaultExceptionHandler  extends ResponseEntityExceptionHandler{
	@ExceptionHandler({DateInvalidException.class})

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage badRequest(Exception e)
	{
		return new ErrorMessage(null, e.getMessage());
	}

	
	@ExceptionHandler(NoDataFoundedException.class)
	public final ResponseEntity<Object> checkLoginCredentials(Exception exception){
	return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.NO_CONTENT, exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(EmailExistsException.class)
	public final ResponseEntity<Object> handleEmailExists(Exception exception){
	return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.NO_CONTENT, exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(TestDataInvalidException.class)
	public final ResponseEntity<Object> TestDataInvalid(Exception exception){
	return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.NO_CONTENT, exception.getMessage()), HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(DataMismatchExcpetion.class)
	public final ResponseEntity<Object> checkAddAccountDetails(Exception exception){
		return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Object> somethingWentWrong(Exception exception){
		return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CannotRetrieveDataException.class)
	public final ResponseEntity<Object> retrieveData(Exception exception){
		return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> internalServerError(Exception exception){
		return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	@ExceptionHandler(DataEnteringException.class)
	public final ResponseEntity<Object> DataEnteringException(Exception exception){
			return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	} 
	@ExceptionHandler(DataMergingException.class)
	public final ResponseEntity<Object> DataMergingException(Exception exception){
			return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	} 
	
	@ExceptionHandler(QuestionsNotFoundException.class)
	public final ResponseEntity<Object> QuestionsNotFoundException(Exception exception){
		return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
	
	class ErrorMessage{
		private HttpStatus message;
		private String details;
		
		public ErrorMessage(HttpStatus message, String details) {
			super();
			this.message = message;
			this.details = details;
		}

		public HttpStatus getMessage() {
			return message;
		}

		public void setMessage(HttpStatus message) {
			this.message = message;
		}

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}
		
		
	}
