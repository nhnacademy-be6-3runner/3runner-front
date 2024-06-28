package com.nhnacademy.bookstore.member.member.exception;

public class LoginOauthEmailException extends RuntimeException {
	public LoginOauthEmailException(Member.Login) {super(Member.Login.toString()+"로 회원가입된 이메일입니다.");}

}
