package textExcel;

public class ErrorException extends RuntimeException {
	private String errorMessage;
	
	public ErrorException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}
