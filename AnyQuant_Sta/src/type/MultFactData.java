package type;

import java.util.ArrayList;

public class MultFactData {

	public String attribute;
	public ArrayList<String> stolist;
	public double incPerc;
	public int days;
	public int sumsto;

	public MultFactData(String attribute, ArrayList<String> stolist, double incPerc, int days, int sumsto) {
		super();
		this.attribute = attribute;
		this.stolist = stolist;
		this.incPerc = incPerc;
		this.days = days;
		this.sumsto = sumsto;
	}
	
	public MultFactData(){
		this.attribute = null;
		this.stolist = null;
		this.incPerc = 0.0;
		this.days = 0;
		this.sumsto = 0;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public ArrayList<String> getStolist() {
		return stolist;
	}

	public void setStolist(ArrayList<String> stolist) {
		this.stolist = stolist;
	}

	public double getIncPerc() {
		return incPerc;
	}

	public void setIncPerc(double incPerc) {
		this.incPerc = incPerc;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getSumsto() {
		return sumsto;
	}

	public void setSumsto(int sumsto) {
		this.sumsto = sumsto;
	}

}
