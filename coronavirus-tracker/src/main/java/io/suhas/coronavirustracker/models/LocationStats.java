package io.suhas.coronavirustracker.models;

public class LocationStats {

	private String state;
	private String country;
	private int current_date_count;
	private int diffFromPrevDay;

	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}

	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getCurrent_date_count() {
		return current_date_count;
	}

	public void setCurrent_date_count(int current_date_count) {
		this.current_date_count = current_date_count;
	}

	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", current_date_count=" + current_date_count
				+ "]";
	}
}
