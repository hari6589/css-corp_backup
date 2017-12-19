package samples.pojo;

public class BSROWebServiceWifiResponse {

	private String responseCode;
	private String responseMessage;
	
	public BSROWebServiceWifiResponse() { }

	public BSROWebServiceWifiResponse(String responseCode,String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String toString(String callBack) {
		return callBack + "({\"responseCode\":\"" + this.responseCode + "\",\"responseMessage\":\"" + this.responseMessage + "\"})";
	}
	
}
