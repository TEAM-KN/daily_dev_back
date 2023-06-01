//package com.daily.global.exception.dto;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Getter
//@NoArgsConstructor
//public class ErrorResponse {
//
//    private LocalDateTime errorTime;
//    private int status;
//    private String errorMessage;
//    private String errorCode;
//    private List<FieldError> errors;
//    private Object body;
//
//
//    private ErrorResponse(ErrorCode code, List<FieldError> errors) {
//        this.errorTime = LocalDateTime.now();
//        this.status = code.getHttpStatus().value();
//        this.errorMessage = code.getMessage();
//        this.errorCode = code.getHttpStatus().value();
//        this.errors = errors;
//        this.body = new ArrayList<>();
//    }
//
//    private ErrorResponse(ErrorCode code, Object body) {
//        this.errorTime = LocalDateTime.now();
//        this.status = code.getHttpStatus().value();
//        this.errorMessage = code.getMessage();
//        this.errorCode = code.getHttpStatus().value();
//        this.errors = new ArrayList<>();
//        this.body = body != null ? body : new ArrayList<>();
//    }
//
//    private ErrorResponse(ErrorCode code) {
//        this.errorTime = LocalDateTime.now();
//        this.status = code.getStatus();
//        this.errorMessage = code.getMessage();
//        this.errorCode = code.getCode();
//        this.errors = new ArrayList<>();
//        this.body = new ArrayList<>();
//    }
//
//    /*
//     * @RequestBody body에 전달된 객체로부터 에러 발생시 BindingResult 객체를 받아올 수 있음.
//     * */
//    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
//        return new ErrorResponse(code, FieldError.of(bindingResult));
//    }
//
//    public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
//        return new ErrorResponse(code, errors);
//    }
//
//    public static ErrorResponse of(final ErrorCode code, final Object body) {
//        return new ErrorResponse(code, body);
//    }
//
//    public static ErrorResponse of(final ErrorCode code) {
//        return new ErrorResponse(code);
//    }
//
//    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
//        final String value = e.getValue() == null ? "" : e.getValue().toString();
//        final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
//        return new ErrorResponse(ErrorCode.DATA_NOT_FOUND, errors);
//    }
//
//    @Getter
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    public static class FieldError {
//        private String field;
//        private String value;
//        private String reason;
//
//        private FieldError(final String field, final String value, final String reason) {
//            this.field = field;
//            this.value = value;
//            this.reason = reason;
//        }
//
//        public static List<FieldError> of(final String field, final String value, final String reason) {
//            List<FieldError> fieldErrors = new ArrayList<>();
//            fieldErrors.add(new FieldError(field, value, reason));
//            return fieldErrors;
//        }
//
//        private static List<FieldError> of(final BindingResult bindingResult) {
//            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
//            return fieldErrors.stream()
//                    .map(error -> new FieldError(
//                            error.getField(),
//                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
//                            error.getDefaultMessage()))
//                    .collect(Collectors.toList());
//        }
//    }
//
//}
