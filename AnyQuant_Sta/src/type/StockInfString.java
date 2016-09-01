package type;
/**
 * 股票最新数据
 * @author YU Fan
 * @date 2016年5月13日
 */
public class StockInfString {
	/**股票代码*/
	private String symbol;
	/**名称*/
	private String name;
	/**最新价*/
	private String trade;
	/**涨跌额*/
	private String pricechange;
	/**涨跌幅*/
	private String changepercent;
	/**买入*/
	private String buy;
	/**卖出*/
	private String sell;
	/**昨天收盘价*/
	private String settlement;
	/**今天开盘价*/
	private String open;
	/**最高价*/
	private String high;
	/**最低价*/
	private String low;
	/**成交量*/
	private String volume;
	/**成交额*/
	private String amount;
	/**简码*/
	private String code;
	/**时间*/
	private String ticktime;
	
	public StockInfString()
	{
		super();
	}
	public StockInfString(String symbol, String name, String trade,
			String pricechange, String changepercent, String buy, String sell,
			String settlement, String open, String high, String low,
			String volume, String amount, String code, String ticktime) {
		super();
		this.symbol = symbol;
		this.name = name;
		this.trade = trade;
		this.pricechange = pricechange;
		this.changepercent = changepercent;
		this.buy = buy;
		this.sell = sell;
		this.settlement = settlement;
		this.open = open;
		this.high = high;
		this.low = low;
		this.volume = volume;
		this.amount = amount;
		this.code = code;
		this.ticktime = ticktime;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getPricechange() {
		return pricechange;
	}
	public void setPricechange(String pricechange) {
		this.pricechange = pricechange;
	}
	public String getChangepercent() {
		return changepercent;
	}
	public void setChangepercent(String changepercent) {
		this.changepercent = changepercent;
	}
	public String getBuy() {
		return buy;
	}
	public void setBuy(String buy) {
		this.buy = buy;
	}
	public String getSell() {
		return sell;
	}
	public void setSell(String sell) {
		this.sell = sell;
	}
	public String getSettlement() {
		return settlement;
	}
	public void setSettlement(String settlement) {
		this.settlement = settlement;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTicktime() {
		return ticktime;
	}
	public void setTicktime(String ticktime) {
		this.ticktime = ticktime;
	}
	@Override
	public String toString() {
		return "NewStockDataString [symbol=" + symbol + ", name=" + name
				+ ", trade=" + trade + ", pricechange=" + pricechange
				+ ", changepercent=" + changepercent + ", buy=" + buy
				+ ", sell=" + sell + ", settlement=" + settlement + ", open="
				+ open + ", high=" + high + ", low=" + low + ", volume="
				+ volume + ", amount=" + amount + ", code=" + code
				+ ", ticktime=" + ticktime + "]";
	}
	
}
