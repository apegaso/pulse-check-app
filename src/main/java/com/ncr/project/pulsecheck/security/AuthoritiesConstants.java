package com.ncr.project.pulsecheck.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String NCR_ADMIN = "ROLE_NCR_ADMIN";
    
    public static final String CLIENT_LEAD = "ROLE_CLIENT_LEAD";
    
    public static final String PARTICIPANT = "ROLE_PARTICIPANT";
    
    private AuthoritiesConstants() {
    }
}
