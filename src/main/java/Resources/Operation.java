package Resources;

public enum Operation {
	LOGIN, LOGOUT, CHANGE_PASSWORD, REGISTER_USER, DELETE_ACCOUNT, ACCOUNT_LOCKOUT;
	
	Operation() {
	}
	
	public static Operation fromValue(String value) {
		return valueOf(value);
	}
	
	@Override
	public String toString() {
		return name();
	}
}
