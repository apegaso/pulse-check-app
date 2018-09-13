package com.ncr.project.pulsecheck.web.rest.vm;

import java.io.Serializable;

/**
 * View Model object for storing a user id and email details.
 */
public class UserEmailVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userExtId;

    private String email;

    public UserEmailVM() {

    }

    public UserEmailVM(Long userExtId, String email) {
        this.userExtId = userExtId;
        this.email = email; 
	}

	/**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the userExtId
     */
    public Long getUserExtId() {
        return userExtId;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param userExtId the userExtId to set
     */
    public void setUserExtId(Long userExtId) {
        this.userExtId = userExtId;
    }


    @Override
    public String toString() {
        return "UserEmailVM{" +
            "userExtId=" + getUserExtId() +
            ", email=" + getEmail() + "" +
            "}";
    }
}