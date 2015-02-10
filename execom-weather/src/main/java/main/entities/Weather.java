package main.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity                                        
@Table(name="weather", uniqueConstraints = @UniqueConstraint(columnNames={"date","city_id"}))
public class Weather {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer weatherId;

	private long date;
	private int temperature;
	private int humidity;
	private int pressure;

	@ManyToOne(fetch=FetchType.LAZY, cascade={ CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name="city_id")
	@JsonBackReference
	private City city;

	public Weather() {

	}

	public Weather( long date, int temperature, int humidity, int pressure) {
		super();
		this.date = date;
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
	}

	public Integer getWeatherId() {
		return weatherId;
	}

	public void setWeatherId(Integer weatherId) {
		this.weatherId = weatherId;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Weather [weatherId=" + weatherId + ", date=" + date + ", temperature=" + temperature + ", humidity=" + humidity
				+ ", pressure=" + pressure + ", city=" + city + "]";
	}
}