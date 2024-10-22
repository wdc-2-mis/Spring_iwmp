package app.bean;

public class DisApprovalBean {

	private char approval_req;
	private String approval_req_date;
	private int stCode;
	private String stName;
	public char getApproval_req() {
		return approval_req;
	}
	public void setApproval_req(char approval_req) {
		this.approval_req = approval_req;
	}
	public String getApproval_req_date() {
		return approval_req_date;
	}
	public void setApproval_req_date(String approval_req_date) {
		this.approval_req_date = approval_req_date;
	}
	public int getStCode() {
		return stCode;
	}
	public void setStCode(int stCode) {
		this.stCode = stCode;
	}
	public String getStName() {
		return stName;
	}
	public void setStName(String stName) {
		this.stName = stName;
	}
	
	
}
