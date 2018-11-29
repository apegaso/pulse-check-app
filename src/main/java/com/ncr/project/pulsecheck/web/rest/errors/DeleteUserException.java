package com.ncr.project.pulsecheck.web.rest.errors;

public class DeleteUserException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public DeleteUserException(String message) {
        super(ErrorConstants.DELETE_USER_TYPE, String.format("Unable to delete user: %s",message), "userManagement", "deleteerror");
    }
}
