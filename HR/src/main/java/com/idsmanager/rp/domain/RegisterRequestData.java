package com.idsmanager.rp.domain;

import java.util.List;


public class RegisterRequestData {
	    private  List<AuthenticateRequest> authenticateRequests;
	    private  List<RegisterRequest> registerRequests;
	    private String type;
		public List<AuthenticateRequest> getAuthenticateRequests() {
			return authenticateRequests;
		}
		public void setAuthenticateRequests(
				List<AuthenticateRequest> authenticateRequests) {
			this.authenticateRequests = authenticateRequests;
		}
		public List<RegisterRequest> getRegisterRequests() {
			return registerRequests;
		}
		public void setRegisterRequests(List<RegisterRequest> registerRequests) {
			this.registerRequests = registerRequests;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	    
}
