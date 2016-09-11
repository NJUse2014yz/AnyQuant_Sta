package type;

public class StockPair {
	public String sid;
	public double correlation;
	
	public StockPair(String sid, double correlation) {
		super();
		this.sid = sid;
		this.correlation = correlation;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public double getCorrelation() {
		return correlation;
	}
	public void setCorrelation(double correlation) {
		this.correlation = correlation;
	}
	@Override
	public String toString() {
		return "StockPair [sid=" + sid + ", correlation=" + correlation + "]";
	}
}
