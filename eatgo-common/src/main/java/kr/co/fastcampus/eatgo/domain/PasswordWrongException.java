package kr.co.fastcampus.eatgo.domain;

public class PasswordWrongException extends RuntimeException {

    public PasswordWrongException() {
        super("Password is wrong");
    }

}
