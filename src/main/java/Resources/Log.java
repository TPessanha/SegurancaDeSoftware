package Resources;

import java.util.Date;

public class Log {
	private String usernameUsed;
	private Date dateTime;
	private Operation operation;
	private boolean success;
	private String notes;
	
	public Log(String usernameUsed, Date dateTime, Operation operation, boolean success, String notes) {
		this.usernameUsed = usernameUsed;
		this.dateTime = dateTime;
		this.operation = operation;
		this.success = success;
		this.notes = notes;
	}
	
	public String getUsernameUsed() {
		return usernameUsed;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public Operation getOperation() {
		return operation;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public String getNotes() {
		return notes;
	}
}
