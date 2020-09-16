package com.cg.otm.security;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String jwt;
	private final Boolean isAdmin;

    public AuthenticationResponse(String jwt,Boolean isAdmin) {
        this.jwt = jwt;
        this.isAdmin = isAdmin;
    }

    public String getJwt() {
        return jwt;
    }
    
    public Boolean getRole() {
        return isAdmin;
    }
}