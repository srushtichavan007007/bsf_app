package com.bsf.app.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bsf.app.exception.DuplicateAccountException;
import com.bsf.app.exception.InsufficientFundException;
import com.bsf.app.exception.InvalidAccountException;

@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(value = InsufficientFundException.class)
	public ResponseEntity<Object> exception(InsufficientFundException exception) {
		return new ResponseEntity<>("Insufficient Fund!", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidAccountException.class)
	public ResponseEntity<Object> exception(InvalidAccountException exception) {
		return new ResponseEntity<>("Invalid Account!", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = DuplicateAccountException.class)
	public ResponseEntity<Object> exception(DuplicateAccountException exception) {
		return new ResponseEntity<>("Duplicate Account!", HttpStatus.CONFLICT);
	}

}
