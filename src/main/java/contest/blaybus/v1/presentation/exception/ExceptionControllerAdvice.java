package contest.blaybus.v1.presentation.exception;

import contest.blaybus.v1.application.exception.AlreadyOurMemberException;
import contest.blaybus.v1.common.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(EmptyDataException.class)
    public ResponseEntity<ApiResponse<?>> handleEmptyDataException(EmptyDataException e) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(e.getMessage()));
    }

    @ExceptionHandler(ParseException.class)
    public ResponseEntity<ApiResponse<?>> handleParseException(ParseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail("날짜 형식이 올바르지 않습니다. 'YYYY-MM-DD' 형식으로 입력해주세요."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<ApiResponse<?>> handleLoginException(LoginFailException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.fail("로그인에 실패했습니다."));
    }

    @ExceptionHandler(AlreadyOurMemberException.class)
    public ResponseEntity<ApiResponse<?>> handleAlreadyOutMemberException(AlreadyOurMemberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFound(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid URL: " + ex.getRequestURL());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(NoResourceFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        response.put("message", "The requested resource was not found: " + ex.getResourcePath());
        response.put("path", ex.getResourcePath());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
