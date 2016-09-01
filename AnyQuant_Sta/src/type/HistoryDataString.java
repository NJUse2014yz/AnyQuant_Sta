package type;

public class HistoryDataString {
	/**日期*/
	private String date;
	/**开盘价*/
	private String open;
	/**收盘价*/
	private String close;
	/**涨跌额*/
	private String increase;
	/**涨跌百分比*/
	private String incrPer;
	/**最低价*/
	private String low;
	/**最高价*/
	private String high;
	/**成交量*/
	private String volume;
	/**成交额*/
	private String amount;
	/**换手率*/
	private String turnover;
	public HistoryDataString(String date, String open, String close,
			String increase, String incrPer, String low, String high,
			String volume, String amount, String turnover) {
		super();
		this.date = date;
		this.open = open;
		this.close = close;
		this.increase = increase;
		this.incrPer = incrPer.substring(0, incrPer.length()-1);
		this.low = low;
		this.high = high;
		this.volume = volume;
		this.amount = amount;
		this.turnover = turnover.substring(0, turnover.length()-1);
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getIncrease() {
		return increase;
	}
	public void setIncrease(String increase) {
		this.increase = increase;
	}
	public String getIncrPer() {
		return incrPer;
	}
	public void setIncrPer(String incrPer) {
		this.incrPer = incrPer.substring(0, incrPer.length()-1);
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTurnover() {
		return turnover;
	}
	public void setTurnover(String turnover) {
		this.turnover = turnover.substring(0, turnover.length()-1);
	}
	@Override
	public String toString() {
		return "HistoryStockDataString [date=" + date + ", open=" + open
				+ ", close=" + close + ", increase=" + increase + ", incrPer="
				+ incrPer + ", low=" + low + ", high=" + high + ", volume="
				+ volume + ", amount=" + amount + ", turnover=" + turnover
				+ "]";
	}
}
