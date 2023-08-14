package com.daily.global.exception;

import com.daily.auth.exception.ExistUserException;
import com.daily.auth.exception.ExpiredTokenException;
import com.daily.auth.exception.InvalidTokenException;
import com.daily.auth.exception.PasswordMatchException;
import com.daily.domain.site.exception.NoSearchSiteException;
import com.daily.domain.user.exception.NoSearchUserException;
import com.daily.global.common.response.CommonResponse;
import com.daily.global.exception.dto.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidRequestBody() {
        return this.makeResponseEntity(ErrorCode.INVALID_PARAMETER);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleInvalidDtoField(final MethodArgumentNotValidException e) {
        FieldError firstFieldError = e.getFieldErrors().get(0);
        String errorMessage = String.format("%s 필드는 %s (전달된 값: %s)", firstFieldError.getField(),
                firstFieldError.getDefaultMessage(), firstFieldError.getRejectedValue());
        return this.makeResponseEntity(ErrorCode.INVALID_PARAMETER, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(final Exception e) {
        log.error("Exception # ", e);
        return this.makeResponseEntity(ErrorCode.INTERVAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            InvalidTokenException.class,
            ExpiredTokenException.class
    })
    public ResponseEntity<Object> handleTokenException(final RuntimeException e) {
        return this.makeResponseEntity(ErrorCode.TOKEN_EXPIRATION, e.getMessage());
    }

    @ExceptionHandler({
            NoSearchUserException.class,
            NoSearchSiteException.class,
            PasswordMatchException.class
    })
    public ResponseEntity<Object> handleNoSearchException(final RuntimeException e) {
        return this.makeResponseEntity(ErrorCode.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({
            DuplicateKeyException.class,
            ExistUserException.class
    })
    public ResponseEntity<Object> handleUnAuthorization(final RuntimeException e) {
        return this.makeResponseEntity(ErrorCode.CONFLICT, e.getMessage());
    }

    private ResponseEntity<Object> makeResponseEntity(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(this.makeResponseEntityBody(errorCode, errorCode.getMessage()));
    }

    private ResponseEntity<Object> makeResponseEntity(final ErrorCode errorCode, final String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(this.makeResponseEntityBody(errorCode, message));
    }

    private CommonResponse makeResponseEntityBody(final ErrorCode errorCode, final String message) {
        return CommonResponse.builder()
                .code(errorCode.getHttpStatus().value())
                .message(message)
                .build();
    }

}
