package com.nhnacademy.bookstore.global.exceptionHandler;
import com.nhnacademy.bookstore.book.book.exception.BookDoesNotExistException;
import com.nhnacademy.bookstore.book.book.exception.CreateBookRequestFormException;
import com.nhnacademy.bookstore.purchase.bookCart.exception.BookCartArgumentErrorException;
import com.nhnacademy.bookstore.purchase.bookCart.exception.BookCartDoesNotExistException;
import com.nhnacademy.bookstore.purchase.cart.exception.CartDoesNotExistException;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseAlreadyExistException;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseDoesNotExistException;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseFormArgumentErrorException;
import com.nhnacademy.bookstore.purchase.purchase.exception.PurchaseNoAuthorizationException;
import com.nhnacademy.bookstore.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.channels.AlreadyBoundException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Error Handler Controller
 * @author 김병우
 */
@RestControllerAdvice
public class WebControllerAdvice {
    /**
     * INTERNAL_SERVER_ERROR 처리 메소드
     * @param ex
     * @param model
     * @return ApiResponse<ErrorResponseForm>
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorResponseForm> runtimeExceptionHandler(Exception ex, Model model) {

        return ApiResponse.fail(500,
                new ApiResponse.Body<>(
                        ErrorResponseForm.builder()
                                .title(ex.getMessage())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .timestamp(ZonedDateTime.now().toString())
                                .build())
        );
    }

    /**
     * BAD_REQUEST 처리 메소드
     * @param ex
     * @param model
     * @return ApiResponse<ErrorResponseForm>
     */
    @ExceptionHandler({
            CreateBookRequestFormException.class,
            AlreadyBoundException.class,
            PurchaseFormArgumentErrorException.class,
            PurchaseAlreadyExistException.class,
            BookCartArgumentErrorException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponseForm> badRequestHandler(Exception ex, Model model) {
        return ApiResponse.badRequestFail(
                new ApiResponse.Body<>(ErrorResponseForm.builder()
                        .title(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(ZonedDateTime.now().toString())
                        .build())
        );

    }

    /**
     * NOT_FOUND 처리 메소드
     * @param ex
     * @param model
     * @return ApiResponse<ErrorResponseForm>
     *
     * @author 정주혁
     */
    @ExceptionHandler({
            PurchaseDoesNotExistException.class,
            BookDoesNotExistException.class,
            BookCartDoesNotExistException.class,
            CartDoesNotExistException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<ErrorResponseForm> notFoundHandler(Exception ex, Model model) {
        return ApiResponse.notFoundFail(
                new ApiResponse.Body<>(ErrorResponseForm.builder()
                        .title(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(ZonedDateTime.now().toString())
                        .build())
        );
    }
    /**
     * METHOD_NOT_ALLOWED 처리 메소드
     * @param ex
     * @param model
     * @return ApiResponse<ErrorResponseForm>
     *
     * @author 정주혁
     */

    @ExceptionHandler({
            PurchaseNoAuthorizationException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<ErrorResponseForm> unauthorizedHandler(Exception ex, Model model) {
        return new ApiResponse<>(
                new ApiResponse.Header(false, HttpStatus.METHOD_NOT_ALLOWED.value()),
                new ApiResponse.Body<>(ErrorResponseForm.builder()
                        .title(ex.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .timestamp(ZonedDateTime.now().toString())
                        .build())
        );
    }
}