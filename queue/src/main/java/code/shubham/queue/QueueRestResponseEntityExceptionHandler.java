package code.shubham.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class QueueRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ NoSuchQueueExistException.class })
    public ResponseEntity<?> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ java.sql.SQLIntegrityConstraintViolationException.class })
    public ResponseEntity<?> handleSQLIntegrityConstraintViolationException(
            java.sql.SQLIntegrityConstraintViolationException exception, WebRequest request) {
        return new ResponseEntity<>(
                exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}