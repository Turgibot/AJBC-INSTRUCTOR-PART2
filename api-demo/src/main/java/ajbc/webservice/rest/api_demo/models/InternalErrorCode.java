package ajbc.webservice.rest.api_demo.models;

public enum InternalErrorCode {

	INVALID_ID(1234);
	
	private int codeNum;
	
	private InternalErrorCode(int codeNum) {
		this.codeNum = codeNum;
	}
	public int getCodeNum() {
		return codeNum;
	}
}
