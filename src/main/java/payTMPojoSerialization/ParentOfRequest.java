package payTMPojoSerialization;

import java.util.List;

public class ParentOfRequest {

	private List<String> status;
	private String subscriptionId;
	private String fromDate;
	
	public List<String> getStatus() {
		return status;
	}
	public void setStatue(List<String> status) {
		this.status = status;
	}
	public String getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	
}
