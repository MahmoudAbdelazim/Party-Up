package com.partyup.service.exception;

public class UserNotAuthenticatedException extends Exception{
    @Override
    public String getMessage() {
        return "User Not Authenticated";
    }
}
