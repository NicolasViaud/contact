package com.viaud.contacts.web.controllers;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Viaud
 */
@ControllerAdvice
public class RestExceptionProcessor {
     
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionProcessor.class);
    
//    @Autowired
//    private MessageSource messageSource;
     
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    @ResponseBody
    public String notFound(HttpServletRequest req, EntityNotFoundException ex) {
        LOG.debug("Error in REST request "+req.getMethod()+" "+req.getRequestURL().toString(),ex);
        return ex.toString();
    }
 
}
