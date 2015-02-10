package main.beans;

import main.entities.City;

public class WeatherPeriodExtracted {

	private City city;
	private long stDate;
	private long endDate;

	public WeatherPeriodExtracted(City city, long stDate, long endDate) {
		super();
		this.city = city;
		this.stDate = stDate;
		this.endDate = endDate;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public long getStDate() {
		return stDate;
	}

	public void setStDate(long stDate) {
		this.stDate = stDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
}