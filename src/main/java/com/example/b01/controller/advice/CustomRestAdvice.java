package com.example.b01.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Log4j2
public class CustomRestAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleException(BindException e) {

        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        if(e.hasErrors()) {

            BindingResult bindingResult = e.getBindingResult();

            bindingResult.getFieldErrors().forEach((fieldError) -> {
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }

        return ResponseEntity.badRequest().body(errorMap);
    }

    //board의 기본키 board_bno를 참조하는 reply의 외래키 bno가 null일 경우 요청자의 잘못이라고 처리!
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        log.error(e);

        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", ""+System.currentTimeMillis());
        errorMap.put("msg", "constraint fails");
        return ResponseEntity.badRequest().body(errorMap);
    }

    //없는 rno 조회시, 예외처리 400 으로 응답
    @ExceptionHandler({
                        //select시 없을때
                        NoSuchElementException.class,
                        //delete시 없을때
                        EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(Exception e) {
        log.error(e);
        Map<String, String> errorMap = new HashMap<>();

        errorMap.put("time", ""+System.currentTimeMillis());
        errorMap.put("msg", "no Such Element Excpetion");
        return ResponseEntity.badRequest().body(errorMap);
    }

}
