package payTMPojoDeSerialization;

import java.util.List;

public class Results {

	private int totalPaymentsCount;
	private int totalPaymentsAmount;
	private ResultInfo resultInfo;
	private List<Result> result;
	
	public int getTotalPaymentsCount() {
		return totalPaymentsCount;
	}
	public void setTotalPaymentsCount(int totalPaymentsCount) {
		this.totalPaymentsCount = totalPaymentsCount;
	}
	public int getTotalPaymentsAmount() {
		return totalPaymentsAmount;
	}
	public void setTotalPaymentsAmount(int totalPaymentsAmount) {
		this.totalPaymentsAmount = totalPaymentsAmount;
	}
	public ResultInfo getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}
	public List<Result> getResult() {
		return result;
	}
	public void setResult(List<Result> result) {
		this.result = result;
	}
	
}
