package app.logic.model;

import app.logic._0_credentialsSaver.model.UserBean;

public class LoginResponse {

	
	private Boolean firstTime;
	private UserBean user;
	private String message;

	public LoginResponse() {
	}

	public Boolean getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Boolean firstTime) {
		this.firstTime = firstTime;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "LoginResponse [firstTime=" + firstTime + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

	
}
