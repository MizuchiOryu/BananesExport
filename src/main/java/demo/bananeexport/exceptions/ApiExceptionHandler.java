package demo.bananeexport.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice
class ApiExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public Map<String,String > handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String,String > errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error ->{
                    errorMap.put(error.getField(),error.getDefaultMessage());
                }
        );
        return errorMap;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ ApiCustomException.class })
    public Map<String,String > handleApiCustomException(ApiCustomException ex) {
        Map<String,String > errorMap = new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }


}