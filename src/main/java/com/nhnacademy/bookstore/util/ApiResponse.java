package com.nhnacademy.bookstore.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter

public class ApiResponse<T>{
    private Header header;

    private Body<T> body;

    public ApiResponse(Header header, Body<T> body) {
        this.header = header;
        this.body = body;
    }

    public ApiResponse(Header header) {
        this.header = header;
    }


    @Setter
    @Getter
    @AllArgsConstructor
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }

    @Setter
    @Getter
    public static class Body<T> {
        private T data;

        public Body(T data) {
            this.data = data;
        }
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(
                new Header(true, 200, "SUCCESS"),
                new Body<>(data)
        );
    }

    public static <T> ApiResponse<T> fail(int errorCode, String errorMessage) {
        return new ApiResponse<>(
                new Header(true, errorCode, errorMessage)
        );
    }

}
