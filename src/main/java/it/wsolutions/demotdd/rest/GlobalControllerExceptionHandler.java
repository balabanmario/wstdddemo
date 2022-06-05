package it.wsolutions.demotdd.rest;

import it.wsolutions.demotdd.service.AccountNotFoundError;
import it.wsolutions.demotdd.service.InvalidAccountError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
class GlobalControllerExceptionHandler {
  @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
  @ExceptionHandler(AccountNotFoundError.class)
  @ResponseBody
  public ErrorInfo handleNotFound(HttpServletRequest req, Exception ex) {
    log.error("Error retrieving account", ex);
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
  @ExceptionHandler(InvalidAccountError.class)
  @ResponseBody
  public ErrorInfo handleInvalid(HttpServletRequest req, Exception ex) {
    log.error("Error retrieving account", ex);
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

}
